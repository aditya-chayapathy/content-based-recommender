package com.assignment2.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;

@Service
public class WebCrawlerService {

    public Boolean crawlJavaWikibooks() {
        String uri = "https://en.wikibooks.org/wiki/Java_Programming";
        Integer maxDepth = 2;
        HashSet<String> urls = new HashSet<>();

        recursiveJavaWordbooksCrawling(uri, urls, 1, maxDepth);
        System.out.println(urls);

        return Boolean.TRUE;
    }

    private void recursiveJavaWordbooksCrawling(String startURI, HashSet<String> urls, Integer currentDepth, Integer maxDepth) {
        if ((!urls.contains(startURI)) && (currentDepth <= maxDepth)) {
            urls.add(startURI);
            Document document;
            try {
                document = Jsoup.connect(startURI).get();
                Elements linksOnPage = document.select("a[href]");
                for (Element page : linksOnPage) {
                    if (page.attr("abs:href").contains("https://en.wikibooks.org/wiki/Java_Programming")) {
                        recursiveJavaWordbooksCrawling(page.attr("abs:href"), urls, currentDepth + 1, maxDepth);
                    }
                }
            } catch (IOException e) {
                System.out.println(startURI);
            }
        }
    }

}
