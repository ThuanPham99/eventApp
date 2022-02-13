package com.assignment.event.service;

import com.assignment.event.entity.Event;
import com.assignment.event.entity.Guest;
import com.assignment.event.entity.GuestInEvent;
import com.assignment.event.repository.EventRepository;
import com.assignment.event.repository.GuestInEventRepository;
import com.assignment.event.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestInEventService {
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private GuestInEventRepository guestInEventRepository;
    public List<Guest> getListGuestByEventID(Integer id){
        List<GuestInEvent> listGuestId = guestInEventRepository.findAllByEventID(id);
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < listGuestId.size();i++){
            guests.add(guestRepository.findByID(listGuestId.get(i).getGuestID()));
        }
        return guests;
    }
    public List<Event> getListEventByGuestID(Integer id){
        List<GuestInEvent> list = guestInEventRepository.findAllByGuestID(id);
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < list.size();i++){
            events.add(eventRepository.findByIDAndStatus(list.get(i).getEventID(),true));
        }
        return events;
    }
    public List<Guest> getList(Integer id){
        List<Guest> guests1 = getListGuestByEventID(id);
        List<Guest> guests2 = guestRepository.findAllByStatus(true);
        guests2.removeAll(guests1);
        return guests2;
    }
    public boolean add(GuestInEvent guestInEvent){
        if(guestInEventRepository.save(guestInEvent)!=null){
            return true;
        }
        return false;
    }
    public GuestInEvent getElement(Integer guestId, Integer eventId){
        return guestInEventRepository.findByEventIDAndGuestID(eventId,guestId);
    }
    public List<GuestInEvent> getAllListGuestinEventbyEventID(Integer id){
        return guestInEventRepository.findAllByEventID(id);
    }
}
