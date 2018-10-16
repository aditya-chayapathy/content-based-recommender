package com.assignment2.services;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;

@Service
public class WebCrawlerService {

    public Boolean crawlPages() throws Exception {
        crawlJavaWikibooks();
        crawlOracleTutorials();

        return Boolean.TRUE;
    }

    public void crawlOracleTutorials() throws Exception {
        String uri = "https://docs.oracle.com/javase/tutorial/";
        Integer maxDepth = 3;
        HashSet<String> urls = new HashSet<>();

        recursiveJavaWordbooksCrawling(uri, urls, 1, maxDepth, "https://docs.oracle.com/javase/tutorial/");

        Directory indexDir = FSDirectory.open(Paths.get("./index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
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
                    doc.add(new TextField("content", paragraph.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements tables = div.getElementsByTag("table");
                for (Element table : tables) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "table", TextField.Store.YES));
                    doc.add(new TextField("content", table.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements discriptionLists = div.getElementsByTag("dl");
                for (Element discriptionList : discriptionLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "descriptionList", TextField.Store.YES));
                    doc.add(new TextField("content", discriptionList.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements unorderedLists = div.getElementsByTag("ul");
                for (Element unorderedList : unorderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "unorderedList", TextField.Store.YES));
                    doc.add(new TextField("content", unorderedList.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements orderedLists = div.getElementsByTag("ol");
                for (Element orderedList : orderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "orderedList", TextField.Store.YES));
                    doc.add(new TextField("content", orderedList.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
            } catch (Exception e) {
                System.out.println(url);
//                e.printStackTrace();
            }
        }
        writer.close();
    }

    public void crawlJavaWikibooks() throws Exception {
        String uri = "https://en.wikibooks.org/wiki/Java_Programming";
        Integer maxDepth = 2;
        HashSet<String> urls = new HashSet<>();

        recursiveJavaWordbooksCrawling(uri, urls, 1, maxDepth, "https://en.wikibooks.org/wiki/Java_Programming");

        Directory indexDir = FSDirectory.open(Paths.get("./index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
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
                    doc.add(new TextField("content", paragraph.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements tables = div.getElementsByTag("table");
                for (Element table : tables) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "table", TextField.Store.YES));
                    doc.add(new TextField("content", table.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements unorderedLists = div.getElementsByTag("ul");
                for (Element unorderedList : unorderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "unorderedList", TextField.Store.YES));
                    doc.add(new TextField("content", unorderedList.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
                Elements orderedLists = div.getElementsByTag("ol");
                for (Element orderedList : orderedLists) {
                    org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                    doc.add(new TextField("url", url, TextField.Store.YES));
                    doc.add(new TextField("type", "orderedList", TextField.Store.YES));
                    doc.add(new TextField("content", orderedList.text(), TextField.Store.YES));
                    writer.addDocument(doc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
    }

    public HashSet<String> queryIndex(String content, Integer hitsPerPage) throws Exception {
        Directory indexDir = FSDirectory.open(Paths.get("./index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();

        Query q = new QueryParser("content", analyzer).parse(content);
        IndexReader reader = DirectoryReader.open(indexDir);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage * 2);
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        HashSet<String> result = new HashSet<>();
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            org.apache.lucene.document.Document d = searcher.doc(docId);
            result.add(d.get("content"));
            if (result.size() == hitsPerPage) {
                break;
            }
        }
        reader.close();

        return result;
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
