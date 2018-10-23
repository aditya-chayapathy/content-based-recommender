package com.assignment2.services;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;

@Service
public class WebCrawlerService {

    public Boolean crawlPages() throws Exception {
        File file = new File("./index");
        try {
            for (File content : file.listFiles()) {
                content.delete();
            }
        } catch (NullPointerException e) {
            System.out.println("Index folder doesn't exist. Creating a new one.");
        }
        crawlJavaWikibooks();
        crawlOracleTutorials();

        return Boolean.TRUE;
    }

    private void crawlOracleTutorials() throws Exception {
        String uri = "https://docs.oracle.com/javase/tutorial/";
        Integer maxDepth = 3;
        HashSet<String> urls = new HashSet<>();

        recursiveJavaWordbooksCrawling(uri, urls, 1, maxDepth, "https://docs.oracle.com/javase/tutorial/");

        Directory indexDir = FSDirectory.open(new File("./index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter writer = new IndexWriter(indexDir, config);

        for (String url : urls) {
            try {
                Document document = Jsoup.connect(url).get();
                Element div = document.getElementById("PageContent");
                Elements paragraphs = div.getElementsByTag("p");
                for (Element paragraph : paragraphs) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "paragraph", TextField.Store.YES));
                    doc.add(new TextField("actualContent", paragraph.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(paragraph.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements tables = div.getElementsByTag("table");
                for (Element table : tables) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "table", TextField.Store.YES));
                    doc.add(new TextField("actualContent", table.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(table.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements discriptionLists = div.getElementsByTag("dl");
                for (Element discriptionList : discriptionLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "descriptionList", TextField.Store.YES));
                    doc.add(new TextField("actualContent", discriptionList.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(discriptionList.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements unorderedLists = div.getElementsByTag("ul");
                for (Element unorderedList : unorderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "unorderedList", TextField.Store.YES));
                    doc.add(new TextField("actualContent", unorderedList.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(unorderedList.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements orderedLists = div.getElementsByTag("ol");
                for (Element orderedList : orderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "orderedList", TextField.Store.YES));
                    doc.add(new TextField("actualContent", orderedList.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(orderedList.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
            } catch (Exception e) {
                System.out.println(url);
//                e.printStackTrace();
            }
        }
        writer.close();
    }

    public String processStemStopWords(String text) throws Exception {
        TokenStream tokenStream = new StandardTokenizer(new StringReader(text));
        tokenStream = new StopFilter(tokenStream, EnglishAnalyzer.getDefaultStopSet());
        tokenStream = new PorterStemFilter(tokenStream);

        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttr = tokenStream.getAttribute(CharTermAttribute.class);
        tokenStream.reset();
        try {
            while (tokenStream.incrementToken()) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(charTermAttr.toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return sb.toString();
        }
        return sb.toString();
    }

    private void crawlJavaWikibooks() throws Exception {
        String uri = "https://en.wikibooks.org/wiki/Java_Programming";
        Integer maxDepth = 2;
        HashSet<String> urls = new HashSet<>();

        recursiveJavaWordbooksCrawling(uri, urls, 1, maxDepth, "https://en.wikibooks.org/wiki/Java_Programming");

        Directory indexDir = FSDirectory.open(new File("./index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter writer = new IndexWriter(indexDir, config);

        for (String url : urls) {
            try {
                Document document = Jsoup.connect(url).get();
                Element div = document.getElementsByClass("mw-parser-output").get(0);
                Elements paragraphs = div.getElementsByTag("p");
                for (Element paragraph : paragraphs) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "paragraph", TextField.Store.YES));
                    doc.add(new TextField("actualContent", paragraph.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(paragraph.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements tables = div.getElementsByTag("table");
                for (Element table : tables) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "table", TextField.Store.YES));
                    doc.add(new TextField("actualContent", table.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(table.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements unorderedLists = div.getElementsByTag("ul");
                for (Element unorderedList : unorderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "unorderedList", TextField.Store.YES));
                    doc.add(new TextField("actualContent", unorderedList.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(unorderedList.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements orderedLists = div.getElementsByTag("ol");
                for (Element orderedList : orderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "orderedList", TextField.Store.YES));
                    doc.add(new TextField("actualContent", orderedList.text(), TextField.Store.YES));
                    doc.add(new TextField("stemmedContent", processStemStopWords(orderedList.text()), TextField.Store.YES));
                    writer.addDocument(doc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
    }

    private void recursiveJavaWordbooksCrawling(String startURI, HashSet<String> urls, Integer currentDepth, Integer maxDepth, String filter) {
        if ((!urls.contains(startURI)) && (currentDepth <= maxDepth)) {
            Document document;
            try {
                document = Jsoup.connect(startURI).get();
                urls.add(startURI);
                Elements linksOnPage = document.select("a[href]");
                for (Element page : linksOnPage) {
                    if (page.attr("abs:href").contains(filter)) {
                        recursiveJavaWordbooksCrawling(page.attr("abs:href"), urls, currentDepth + 1, maxDepth, filter);
                    }
                }
            } catch (IOException e) {
                System.out.println(startURI);
            }
        }
    }

}
