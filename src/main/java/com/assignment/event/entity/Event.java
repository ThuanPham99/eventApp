package com.assignment.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "Event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    public Event(String name, String description, String location, Date timeStart, String adminID, String staffID, boolean status) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.timeStart = timeStart;
        this.adminID = adminID;
        this.staffID = staffID;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer ID;
    @Column(name = "Name")
    private String name;
    @Column(name ="Description")
    private String description;
    @Column(name = "location")
    private String location;
    @Column(name = "time_start")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date timeStart;
    @Column(name = "AdminID")
    private String adminID;
    @Column(name = "StaffID")
    private String staffID;
    @Column(name = "Status")
    private boolean status;

}
