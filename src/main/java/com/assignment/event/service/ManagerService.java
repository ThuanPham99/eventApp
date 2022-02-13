package com.assignment.event.service;

import com.assignment.event.entity.Manager;
import com.assignment.event.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    public ManagerService() {
    }

    public Manager checkLogin(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return null;
        }
        Manager manager;
        try {
            manager = managerRepository.findByUserIDAndPassword(username,password);
        }catch (Exception e){
            manager = null;
        }
        return manager;
    }
    public boolean checkExist(String id){
        Manager manager = managerRepository.findByUserID(id);
        if(manager != null){
            return true;
        }
        return false;
    }
    public Manager getManager(String id){
        return managerRepository.findByUserID(id);
    }
}
