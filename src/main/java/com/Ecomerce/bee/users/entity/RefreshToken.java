package com.Ecomerce.bee.users.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId;

    //    @Column(unique = true)
    private String refreshToken;

    private Instant expiry;

    @OneToOne
    private User user;
}
