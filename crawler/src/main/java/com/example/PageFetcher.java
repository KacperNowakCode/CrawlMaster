package com.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.*;

public class PageFetcher {
    public static void fetch(String url, String hostname, int timeout) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> crawl(url, hostname));

        try {
            future.get(timeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("[Timeout] Skipping " + url);
            future.cancel(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static void crawl(String url, String baseHostname) {
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String absLink = link.absUrl("href");
                if (!absLink.isEmpty() && absLink.contains(baseHostname)) {
                    System.out.println("Found link: " + absLink);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to fetch " + url + ": " + e.getMessage());
        }
    }
}
