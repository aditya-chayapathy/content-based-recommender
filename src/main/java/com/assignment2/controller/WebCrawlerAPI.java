package com.assignment2.controller;

import com.assignment2.services.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webCrawler")
public class WebCrawlerAPI {

    @Autowired
    WebCrawlerService webCrawlerService;

    @RequestMapping(value = "/crawlJavaWikibooks", method = RequestMethod.POST)
    public Boolean crawlJavaWikibooks() {
        return webCrawlerService.crawlJavaWikibooks();
    }
}
