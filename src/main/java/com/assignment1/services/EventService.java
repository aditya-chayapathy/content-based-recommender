package com.assignment1.services;


import com.assignment1.pojo.Event;
import com.assignment1.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public Event addEvent(Long userId, String eventType, String eventInfo) {
        Event event = new Event(userId, System.currentTimeMillis(), eventType, eventInfo);
        eventRepository.save(event);

        return event;
    }

    public Event removeEvent(Long userId, Long timestamp, String eventType) {
        Event event = eventRepository.findByUserIdAndTimestampAndEventType(userId, timestamp, eventType);
        eventRepository.delete(event);

        return event;
    }

    public List<Event> getEventsForUser(Long userId, String eventType) {
        if (eventType == null) {
            return eventRepository.findByUserId(userId);
        }

        return eventRepository.findByUserIdAndEventType(userId, eventType);
    }

}
