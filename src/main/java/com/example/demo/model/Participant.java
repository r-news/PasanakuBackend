package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    User user;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="meet_id")
    Meet meet;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    Rol rol;


    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Bet bet;

    public Participant(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meet getMeet() {
        return meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
