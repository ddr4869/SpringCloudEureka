package com.delivery.user_service.entity;

import com.delivery.user_service.dto.UserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String babyName;

    private String monthAfterBirth;

    private String dueDate;

    @Column(unique = true, nullable = false)
    private String email;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Builder
    public User(Long id, String name, String babyName, String monthAfterBirth, String email, String profileImageUrl, Role role) {
        this.id = id;
        this.name = name;
        this.babyName = babyName;
        this.monthAfterBirth = monthAfterBirth;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.profileImageUrl = picture;

        return this;
    }

    public void update(UserRequest userRequest) {
        if(userRequest.getName() != null && !userRequest.getName().isBlank()){
            this.name = userRequest.getName();
        }
        if(userRequest.getBabyName() != null && !userRequest.getBabyName().isBlank()){
            this.babyName = userRequest.getBabyName();
        }
        if(userRequest.getMonthAfterbirth() != null && !userRequest.getMonthAfterbirth().isBlank()){
            this.monthAfterBirth = userRequest.getMonthAfterbirth();
        }
        if(userRequest.getDueDate() != null && !userRequest.getDueDate().isBlank()) {
            this.dueDate = userRequest.getDueDate();
        }
    }
}
