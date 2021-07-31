package com.pinstagram.domain.entity.contents;


import com.pinstagram.domain.entity.auth.AccountEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contents")
@Getter
@Setter
public class ContentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contentsId;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String fullAddress;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Column
    private LocalDateTime createAt;

    @Column
    private LocalDateTime updateAt;
}
