package com.assignment2.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class WebCrawlerService {

    public Boolean crawlJavaWikibooks() {
        String uri = "https://en.wikibooks.org/wiki/Java_Programming";
        Integer maxDepth = 2;
        HashSet<String> urls = new HashSet<>();

        recursiveJavaWordbooksCrawling(uri, urls, 1, maxDepth);

        List<String> docs = new ArrayList<>();
        for (String url : urls) {
            try {
                Document document = Jsoup.connect(url).get();
                Element div = document.getElementsByClass("mw-parser-output").get(0);
                Elements paragraphs = div.getElementsByTag("p");
                for (Element paragraph : paragraphs) {
                    docs.add(paragraph.text());
                }
                Elements tables = div.getElementsByTag("table");
                for (Element table : tables) {
                    docs.add(table.text());
                }
                Elements unorderedLists = div.getElementsByTag("ul");
                for (Element unorderedList : unorderedLists) {
                    docs.add(unorderedList.text());
                }
                Elements orderedLists = div.getElementsByTag("ol");
                for (Element orderedList : orderedLists) {
                    docs.add(orderedList.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Boolean.TRUE;
    }

    private void recursiveJavaWordbooksCrawling(String startURI, HashSet<String> urls, Integer currentDepth, Integer maxDepth) {
        if ((!urls.contains(startURI)) && (currentDepth <= maxDepth)) {
            Document document;
            try {
                document = Jsoup.connect(startURI).get();
                urls.add(startURI);
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
