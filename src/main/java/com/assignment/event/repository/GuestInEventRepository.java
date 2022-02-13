package com.assignment.event.repository;

import com.assignment.event.entity.GuestInEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestInEventRepository extends JpaRepository<GuestInEvent,Integer> {
    List<GuestInEvent> findAllByEventID(Integer id);
    void deleteByEventIDAndGuestID(Integer eventID,Integer guestID);
    void deleteAllByGuestID(Integer id);
    List<GuestInEvent> findAllByGuestID(Integer id);
    GuestInEvent findByEventIDAndGuestID(Integer evenId,Integer guestId);
}
