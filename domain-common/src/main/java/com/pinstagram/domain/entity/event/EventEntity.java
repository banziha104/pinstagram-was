package com.pinstagram.domain.entity.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "event")
@Getter
@Setter
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String fullAddress;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;
}
