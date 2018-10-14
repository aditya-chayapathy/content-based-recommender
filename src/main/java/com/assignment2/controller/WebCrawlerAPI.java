package com.assignment2.controller;

import com.assignment2.services.IndexService;
import com.assignment2.services.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webCrawler")
public class WebCrawlerAPI {

    @Autowired
    WebCrawlerService webCrawlerService;

    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/crawlJavaWikibooks", method = RequestMethod.POST)
    public Boolean crawlJavaWikibooks() throws Exception {
        return webCrawlerService.crawlJavaWikibooks();
    }

    @RequestMapping(value = "/queryIndex", method = RequestMethod.GET)
    public List<String> queryIndex(@RequestParam(name = "content") String content,
                                   @RequestParam(name = "hits") Integer hits) throws Exception {
        return webCrawlerService.queryIndex(content, hits);
    }

    @RequestMapping(value = "/dataForUIRendering", method = RequestMethod.GET)
    public Map<String, List<String>> dataForUIRendering() {
        return indexService.dataForUIRendering();
    }

}
