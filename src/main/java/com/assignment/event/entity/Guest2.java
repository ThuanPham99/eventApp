package com.assignment.event.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Guest2  {
    private Integer ID;
    private String Name;
    private String numberphone;
    private String mail;
    private Integer role;
    private boolean status;
    private String check;

    public Guest2(Guest guest) {
        this.ID = guest.getID();
        this.Name = guest.getName();
        this.numberphone = guest.getNumberphone();
        this.mail = guest.getMail();
        this.role = guest.getRole();
        this.status = guest.isStatus();
    }
}
