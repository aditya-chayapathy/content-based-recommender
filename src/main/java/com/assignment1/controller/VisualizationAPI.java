package com.assignment1.controller;

import com.assignment1.services.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visualization")
public class VisualizationAPI {

    @Autowired
    VisualizationService visualizationService;

    @RequestMapping(value = "/getKeyPressStats", method = RequestMethod.GET)
    public Map getKeyPressStats(@RequestParam(value = "userId") Long userId) {
        return visualizationService.getKeyPressStats(userId);
    }

    @RequestMapping(value = "/getTimeSpentInAppStats", method = RequestMethod.GET)
    public List getTimeSpentInAppStats(@RequestParam(value = "userId") Long userId) {
        return visualizationService.getTimeSpentInAppStats(userId);
    }

    @RequestMapping(value = "/getMouseTrajectoryStats", method = RequestMethod.GET)
    public Map getMouseTrajectoryStats(@RequestParam(value = "userId") Long userId) {
        return visualizationService.getMouseTrajectoryStats(userId);
    }

    @RequestMapping(value = "/getActionCounts", method = RequestMethod.GET)
    public Map getActionCounts(@RequestParam(value = "userId") Long userId) {
        return visualizationService.getActionCounts(userId);
    }

    @RequestMapping(value = "/getHighlightedWordsStats", method = RequestMethod.GET)
    public Map getHighlightedWordsStats(@RequestParam(value = "userId") Long userId) {
        return visualizationService.getHighlightedWordsStats(userId);
    }

}
