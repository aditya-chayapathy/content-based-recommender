package com.assignment1.controller;

import com.assignment1.pojo.Event;
import com.assignment1.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventAPI {

    @Autowired
    EventService eventService;

    @RequestMapping(value = "/addEvent", method = RequestMethod.POST)
    public Event addEvent(@RequestParam(value = "userId") Long userId,
                          @RequestParam(value = "eventType") String eventType,
                          @RequestParam(value = "eventInfo") String eventInfo) {
        return eventService.addEvent(userId, eventType, eventInfo);
    }

    @RequestMapping(value = "/removeEvent", method = RequestMethod.POST)
    public Event removeEvent(@RequestParam(value = "userId") Long userId,
                             @RequestParam(value = "timestamp") Long timestamp,
                             @RequestParam(value = "eventType") String eventType) {
        return eventService.removeEvent(userId, timestamp, eventType);
    }

    @RequestMapping(value = "/getEventsForUser", method = RequestMethod.GET)
    public List<Event> getEventsForUser(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "eventType", required = false) String eventType) {
        return eventService.getEventsForUser(userId, eventType);
    }

}
