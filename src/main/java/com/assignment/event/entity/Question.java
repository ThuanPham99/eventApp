package com.assignment.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "Content")
    private String content;
    @Column(name = "EventID")
    private  Integer eventID;
    @Column(name = "Status")
    private String status;
    @Column(name = "Answer")
    private String answer;
    @Column(name = "GuestID")
    private Integer guestID;
}
