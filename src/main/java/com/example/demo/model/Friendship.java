package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Friendship")
@Table(name = "Friendship")
public class Friendship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_friend;

    @ManyToOne
    @JoinColumn(name="owner") // Campo de la tabla friendship que es fk de User
    @JsonManagedReference
    private User owner;

    @ManyToOne
    @JoinColumn(name="friend") // Campo de la tabla friendship que es fk de User
    private User friend;

    public Friendship() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}