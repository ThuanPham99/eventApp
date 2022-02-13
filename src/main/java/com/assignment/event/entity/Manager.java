package com.assignment.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "Manager")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager implements Serializable {
    @Id
    private String userID;
    @Column(name = "Password")
    private String password;
    @Column(name = "Name")
    private String name;
    @Column(name = "Mail")
    private String mail;
    @Column(name = "NumberPhone")
    private String numberPhone;
    @Column(name = "Role")
    private Integer role;
}
