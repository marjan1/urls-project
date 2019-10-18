package com.url.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {

    public static URL getUrl(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("MailFormed Url");
        }

    }
}
