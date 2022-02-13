package com.assignment.event.service;

import com.assignment.event.entity.Event;
import com.assignment.event.entity.Guest;
import com.assignment.event.repository.GuestInEventRepository;
import com.assignment.event.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private GuestInEventRepository guestInEventRepository;
    public List<Guest> getListGuset(){
        return guestRepository.findAllByStatus(true);
    }
    public Guest findByID(Integer id){
        return guestRepository.findByID(id);
    }
    @Transactional
    public void deleteGuestByID(Integer id){
        guestInEventRepository.deleteAllByGuestID(id);
        Guest guest = guestRepository.findByID(id);
        guest.setStatus(false);
        guestRepository.save(guest);
    }
    public void updateGuest(Integer id,String name, String phone,String mail){
        Guest guest = guestRepository.findByID(id);
        guest.setName(name);
        guest.setMail(mail);
        guest.setNumberphone(phone);
        guestRepository.save(guest);

    }
    public boolean add(Guest g){
        if(guestRepository.save(g)!=null){
            return true;
        }
        return false;
    }
    public Integer insertListGuest(List<Guest> guests){
        int i = 0;
        if(guests.size()>0){
            for (i = 0; i < guests.size(); i++){
                add(guests.get(i));
            }
        }
        return i;
    }
    public Guest checkLogin(Integer id){
        return guestRepository.findByID(id);
    }
}
