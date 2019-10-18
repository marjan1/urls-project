package com.url.util;

import java.util.Arrays;
import java.util.List;

/**
 * Class with constants that are used in the project.
 */
public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "e_advo23r";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    // Authorization Bearer uheauhehgy3u231uh
    public static final String SECRET = "linksPr";
    public static final long EXPIRATION_TIME = 86400000L;

    public static final List<String> IGNORE_WORDS = Arrays.asList("and", "the", "or", "for", "a", "in", "about", "you", "me", "him", "now", "then", "it");
}
