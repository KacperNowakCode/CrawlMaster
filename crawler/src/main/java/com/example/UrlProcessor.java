package com.example;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UrlProcessor {
    private final Set<String> visited = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public boolean isVisited(String url) {
        return visited.contains(url);
    }

    public void addVisited(String url) {
        visited.add(url);
    }

    public static String extractHostname(String urlString) {
        try {
            URL url = new URL(urlString);
            return url.getHost();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid absolute URL");
        }
    }
}
