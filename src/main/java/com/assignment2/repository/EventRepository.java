package com.assignment2.repository;

import com.assignment2.pojo.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByUserIdAndTimestampAndEventType(Long userId, Long timestamp, String eventType);

    List<Event> findByUserId(Long userId);

    List<Event> findByUserIdAndEventType(Long userId, String eventType);

    Integer countByUserIdAndEventType(Long userId, String eventType);

}