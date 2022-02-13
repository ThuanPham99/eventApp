package com.assignment.event.entity;

import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "guestinevent")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestInEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    public GuestInEvent(Integer eventID, Integer guestID) {
        this.eventID = eventID;
        this.guestID = guestID;
    }

    public GuestInEvent(Integer eventID, Integer guestID, String status) {
        this.eventID = eventID;
        this.guestID = guestID;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "EventID")
    private Integer eventID;
    @Column(name = "GuestID")
    private Integer guestID;
    @Column(name = "Status")
    private String status;

}
