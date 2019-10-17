package com.eadvocate.service;

import com.eadvocate.persistence.model.Link;
import com.eadvocate.persistence.model.LinkParam;
import com.eadvocate.persistence.model.LinkTag;
import com.eadvocate.persistence.model.User;
import com.eadvocate.persistence.repo.LinkParamRepository;
import com.eadvocate.persistence.repo.LinkRepository;
import com.eadvocate.persistence.repo.LinkTagRepository;
import com.eadvocate.persistence.repo.UserRepository;
import com.eadvocate.rest.dto.AddLinkDto;
import com.eadvocate.rest.dto.LinkDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class LinkService {

    private LinkRepository linkRepository;
    private LinkParamRepository linkParamRepository;
    private LinkTagRepository linkTagRepository;
    private UserRepository userRepository;


    public LinkDto addLink(AddLinkDto addLinkDto)  {
        log.info("Adding new link with name {}", addLinkDto.getLink());

        Link link = new Link();
        link.setUser(getUser());

        URL url = getUrl(addLinkDto);
        link.setUrl(url.getHost());
        Link savedLink = this.linkRepository.save(link);

        addLinkParams(url, savedLink);

        addLinkTags(addLinkDto, savedLink);

        LinkDto linkDto = new LinkDto().builder()
                .id(savedLink.getId())
                .link(savedLink.getUrl())
                .tags(addLinkDto.getTags())
                .build();

        return linkDto;
    }

    private void addLinkTags(AddLinkDto addLinkDto, Link savedLink) {
        if(addLinkDto.getTags()!= null && !addLinkDto.getTags().isEmpty()){
            addLinkDto.getTags().stream()
                    .forEach(s -> {
                        LinkTag linkTag = new LinkTag();
                        linkTag.setLink(savedLink);
                        linkTag.setTag(s);
                        linkTag.setOccurrences(1);
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

    private URL getUrl(AddLinkDto addLinkDto)  {
        try {
            return new URL(addLinkDto.getLink());
        } catch (MalformedURLException e) {
           throw new RuntimeException("MailFormed Url");
        }

    }

}
