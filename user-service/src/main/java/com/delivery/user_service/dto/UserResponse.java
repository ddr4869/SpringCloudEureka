package com.delivery.user_service.dto;

import com.delivery.user_service.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {
    private Long id;
    private String name;
    private String babyName;
    private String monthAfterBirth;
    private String dueDate;

    public static UserResponse of(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getBabyName(),
                user.getMonthAfterBirth(),
                user.getDueDate()
        );
    }
}
