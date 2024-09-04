package com.delivery.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String babyName;
    private String monthAfterbirth;
    private String dueDate;
}
