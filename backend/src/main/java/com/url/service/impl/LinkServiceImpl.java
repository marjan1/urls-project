package com.url.service.impl;

import com.url.persistence.model.Link;
import com.url.persistence.model.LinkParam;
import com.url.persistence.model.LinkTag;
import com.url.persistence.model.User;
import com.url.persistence.repo.LinkParamRepository;
import com.url.persistence.repo.LinkRepository;
import com.url.persistence.repo.LinkTagRepository;
import com.url.persistence.repo.UserRepository;
import com.url.rest.dto.AddLinkDto;
import com.url.rest.dto.LinkDto;
import com.url.service.LinkService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class LinkServiceImpl implements LinkService {

    private LinkRepository linkRepository;
    private LinkParamRepository linkParamRepository;
    private LinkTagRepository linkTagRepository;
    private UserRepository userRepository;


    public LinkDto addLink(AddLinkDto addLinkDto) {
        log.info("Adding new link with name {}", addLinkDto.getLink());

        Link link = new Link();
        link.setUser(getUser());

        URL url = getUrl(addLinkDto.getLink());
        link.setUrl(url.getHost());
        Link savedLink = this.linkRepository.save(link);

        addLinkParams(url, savedLink);

        String content = getPageContent(url);

        Map<String, Long> sortedFrequencyMap = getWordFrequencyMap(content);

        addLinkTags(addLinkDto, savedLink, sortedFrequencyMap);

        LinkDto linkDto = new LinkDto().builder()
                .id(savedLink.getId())
                .link(savedLink.getUrl())
                .build();

        return linkDto;
    }

    private Map<String, Long> getWordFrequencyMap(String content) {
        List<String> ignoreWords = Arrays.asList("and", "the", "or", "for", "a", "in", "about", "you", "me", "him", "now", "then", "it");
        Map<String, Long> frequencyMap = Arrays.stream(content.split(" "))
                .filter(s -> !s.isEmpty())
                .filter(s -> !ignoreWords.contains(s.toLowerCase()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return frequencyMap.entrySet()
                .stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }



    @Override
    public List<String> suggestedTags(String link) {
        Map<String, Long> tagsMap = new HashMap<>();
        URL url = getUrl(link);
        Optional<List<Link>> existingLinks = this.linkRepository.findAllByUrl(url.getHost());

        if (existingLinks.isPresent()) {
            existingLinks.get().stream().forEach(link1 -> {

                if (url.getQuery() != null && !url.getQuery().isEmpty()) {
                    String[] queryParams;
                    if (url.getQuery().contains("&")) {
                        queryParams = url.getQuery().split("&");
                    } else {
                        queryParams = new String[1];
                        queryParams[0] = url.getQuery();
                    }

                    Optional<List<LinkParam>> linkParams = this.linkParamRepository.findAllByLink(link1);
                    if (linkParams.isPresent()) {
                        List<String> linkParamUrls = linkParams.get().stream().map(linkParam -> linkParam.getParam()).collect(Collectors.toList());
                        if ((new HashSet<>(Arrays.asList(queryParams))).equals(new HashSet<>(linkParamUrls))) {
                            initTagMap(tagsMap, link1);

                        }
                    }
                } else {
                    initTagMap(tagsMap, link1);
                }


            });
        }

        Map<String, Long> sortedTagsMap = tagsMap.entrySet()
                .stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        return new ArrayList(sortedTagsMap.keySet());
    }

    private void initTagMap(Map<String, Long> tagsMap, Link link1) {
        Optional<List<LinkTag>> linkTags = this.linkTagRepository.findAllByLink(link1);
        if (linkTags.isPresent()) {
            linkTags.get().stream().limit(10).forEach(linkTag -> {
                if (tagsMap.containsKey(linkTag.getTag())) {
                    tagsMap.put(linkTag.getTag(), tagsMap.get(linkTag.getTag()) + linkTag.getOccurrences());
                } else {
                    tagsMap.put(linkTag.getTag(), linkTag.getOccurrences());

                }
            });
        }
    }

    private void addLinkTags(AddLinkDto addLinkDto, Link savedLink, Map<String, Long> tagsFromPage) {
        if (addLinkDto.getTags() != null && !addLinkDto.getTags().isEmpty()) {
            addLinkDto.getTags().stream()
                    .forEach(s -> {
                        LinkTag linkTag = new LinkTag();
                        linkTag.setLink(savedLink);
                        linkTag.setTag(s);
                        linkTag.setOccurrences(1l);
                        this.linkTagRepository.save(linkTag);
                    });
        }

        if (tagsFromPage != null && !tagsFromPage.isEmpty()) {
            tagsFromPage.entrySet().stream().limit(10).forEach(s -> {
                LinkTag linkTag = new LinkTag();
                linkTag.setLink(savedLink);
                linkTag.setTag(s.getKey());
                linkTag.setOccurrences(s.getValue());
                this.linkTagRepository.save(linkTag);
            });
        }


    }

    private void addLinkParams(URL url, Link savedLink) {
        if (url.getQuery() != null && !url.getQuery().isEmpty()) {
            String[] queryParams = url.getQuery().split("&");
            Stream.of(queryParams).forEach(s -> {
                LinkParam linkParam = new LinkParam();
                linkParam.setParam(s);
                linkParam.setLink(savedLink);
                this.linkParamRepository.save(linkParam);
            });
        }
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return this.userRepository.findByEmail(currentPrincipalName).get();
    }

    private URL getUrl(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("MailFormed Url");
        }

    }

    private String getPageContent(URL url) {
        BufferedReader br;
        try {

            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            if (br != null) {
                br.close();
            }

            return Jsoup.parse(sb.toString()).text();
            //  return sb.toString().replaceAll("\\<.*?>","");
        } catch (IOException e) {
            throw new RuntimeException("Page content can not be parsed");
        }
    }

}
