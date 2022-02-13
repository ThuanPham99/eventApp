package com.assignment.event.repository;

import com.assignment.event.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest,Integer> {
    Guest findByID(Integer id);
    List<Guest> findAllByStatus(boolean status);

}
