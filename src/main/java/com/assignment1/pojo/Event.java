package com.assignment1.pojo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_TBL")
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long timestamp;
    private String eventType;
    private String eventInfo;

    public Event() {
        super();
    }

    public Event(Long userId, Long timestamp, String eventType, String eventInfo) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.eventInfo = eventInfo;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
