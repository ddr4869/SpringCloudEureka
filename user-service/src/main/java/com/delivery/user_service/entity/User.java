package com.delivery.user_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "유저 ID")
    private Long id;

    @Column(unique = true)
    @NotNull
    @Schema(description = "유저 이름")
    private String username;

    @Column()
    @NotNull
    @Schema(description = "유저 비밀번호")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Schema(description = "암호화 알고리즘")
    private EncryptionAlgorithm algorithm;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "권한")
    @Setter
    private List<Authority> authorities = new ArrayList<>();

    @Schema(description = "유저 email")
    private String email;

    @Schema(description = "유저 전화번호")
    private String phoneNumber;

    @Builder
    public User(String username, String passwordHash, String email, String phoneNumber) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.algorithm = EncryptionAlgorithm.BCRYPT;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public void addAuthorities(String auth) {
        if (authorities==null) {
            authorities = new ArrayList<>();
        }
        Authority authority = new Authority(auth, this);
        authorities.add(authority);
    }
    public enum EncryptionAlgorithm {
        BCRYPT, SCRYPT
    }
}
