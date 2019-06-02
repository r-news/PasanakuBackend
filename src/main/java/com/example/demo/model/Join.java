package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Join")
public class Join implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
