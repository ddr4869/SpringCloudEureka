package com.delivery.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@ColumnDefault("ROLE_USER")
    @Column
    private String authority;
    @Column
    private String userName;

    @ManyToOne
    @JoinColumn(name= "user_id")
    @JsonIgnore
    private User user;

    @Builder
    public Authority(String authority, User user) {
        this.authority = authority;
        this.user = user;
        this.userName = user.getUsername();
    }
    public static class Authority_Type {
        public static String ROLE_USER="ROLE_USER";
        public static String ROLE_ADMIN="ROLE_ADMIN";
    }
}
