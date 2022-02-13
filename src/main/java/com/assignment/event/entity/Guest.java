package com.assignment.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "guest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    @Column(name = "Name")
    private String Name;
    @Column(name = "number_phone")
    private String numberphone;
    @Column(name = "Mail")
    private String mail;
    @Column(name = "Role")
    private Integer role;
    @Column(name = "Status")
    private boolean status;

    public Guest(String name, String numberphone, String mail, Integer role, boolean status) {
        Name = name;
        this.numberphone = numberphone;
        this.mail = mail;
        this.role = role;
        this.status = status;
    }
}
