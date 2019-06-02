package com.example.demo.model;

public abstract class FactoryParticipants {
    public Participant createParticipant(Bet bet, User user, Meet meet, Rol rol) {
        Participant participant = new Participant();
        participant.setBet(bet);
        participant.setUser(user);
        participant.setMeet(meet);
        participant.setRol(rol);
        return participant;
    }
}
