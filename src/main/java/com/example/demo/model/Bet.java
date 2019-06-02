package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Bet")
public class Bet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "inversion")
    private int inversion = 0;

    public Bet() {

    }

    public int getInversion() {
        return inversion;
    }

    public void setInversion(int inversion) {
        this.inversion = inversion;
    }
}
