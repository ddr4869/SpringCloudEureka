package com.delivery.order_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindUserByNameResponse {
    private String email;
    private String name;
    private Long userId;

}
