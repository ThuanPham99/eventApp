package com.assignment.event.repository;

import com.assignment.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    List<Event> findAllByStatus(boolean status);
    Event findByID(Integer ID);
    Event findByIDAndStatus(Integer id, boolean status);

}
