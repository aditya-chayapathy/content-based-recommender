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

    @RequestMapping(value = "/crawlPages", method = RequestMethod.POST)
    public Boolean crawlPages() throws Exception {
        return webCrawlerService.crawlPages();
    }

    @RequestMapping(value = "/queryIndex", method = RequestMethod.GET)
    public List<Object> queryIndex(@RequestParam(name = "content") String content,
                                   @RequestParam(name = "hits") Integer hits) throws Exception {
        return indexService.queryIndex(content, hits);
    }

    @RequestMapping(value = "/dataForUIRendering", method = RequestMethod.GET)
    public Map<String, Object> dataForUIRendering() throws Exception {
        return indexService.dataForUIRendering();
    }

}
