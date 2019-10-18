package com.url.service.impl;

import com.url.service.ReaderService;
import com.url.validation.exception.PageNotParsedException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


@Service
@AllArgsConstructor
@Log4j2
public class HtmlReaderServiceImpl implements ReaderService {
    @Override
    public String getTextContent(URL url) {
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

        } catch (IOException e) {
            throw new PageNotParsedException();
        }
    }
}
