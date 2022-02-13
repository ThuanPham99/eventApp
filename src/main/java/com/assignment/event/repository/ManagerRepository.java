package com.assignment.event.repository;

import com.assignment.event.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ManagerRepository extends JpaRepository<Manager,String> {
    Manager findByUserIDAndPassword(String userID, String password);
    Manager findByUserID(String id);

}
