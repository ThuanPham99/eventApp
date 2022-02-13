package com.assignment.event.service;

import com.assignment.event.entity.Event;
import com.assignment.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    public List<Event> getListEvent(){
        return eventRepository.findAllByStatus(true);
    }
//    public boolean checkNull(Event event){
//        return Optional.ofNullable(event)
//                .filter(t -> !StringUtils.isEmpty(t.getName()))
//                .filter(t -> !StringUtils.isEmpty(t.getDescription()))
//                .filter(t -> !StringUtils.isEmpty(t.getLocation()))
//                .filter(t -> !StringUtils.isEmpty(t.getTimeStart()))
//                .isPresent();
//
//    }
    public boolean add(Event event){
        if(eventRepository.save(event)!=null){
            return true;
        }
        return false;
    }
    public Event getEvent(Integer id){
        return eventRepository.findByID(id);
    }
}
