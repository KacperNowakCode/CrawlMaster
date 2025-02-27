package com.example;


import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.concurrent.*;

public class Crawler {
    private final ExecutorService executor;
    private final UrlProcessor urlProcessor;
    private final int timeout;


    public Crawler(int threads, int timeout) {
        this.executor = Executors.newFixedThreadPool(threads);
        this.urlProcessor = new UrlProcessor();
        this.timeout = timeout;
    }


    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            if (System.in.available() == 0) {
                System.err.println("No URLs detected. Hint: cat urls.txt | java Crawler");
                System.exit(1);

            }

            String url;
            while ((url = reader.readLine()) != null) {
                final String targetUrl = url;
                executor.execute(() -> processUrl(targetUrl));

            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processUrl(String url) {
        if (urlProcessor.isVisited(url)) return;

        urlProcessor.addVisited(url);
        String hostname = UrlProcessor.extractHostname(url);
        PageFetcher.fetch(url, hostname, timeout);
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("t", true, "Number of threads to utilize.");
        options.addOption("timeout", true, "Maximum crawl time per URL (seconds).");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            int threads = cmd.hasOption("t") ? Integer.parseInt(cmd.getOptionValue("t")) : 8;
            int timeout = cmd.hasOption("timeout") ? Integer.parseInt(cmd.getOptionValue("timeout")) : 10;

            new Crawler(threads, timeout).start();
        } catch (ParseException e) {
            System.out.println("Error parsing arguments: " + e.getMessage());
        }
    }
}
