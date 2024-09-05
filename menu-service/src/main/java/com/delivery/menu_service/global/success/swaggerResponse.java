package com.delivery.menu_service.global.success;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @param errors Errors가 없다면 응답이 내려가지 않게 처리
 */

public class swaggerResponse {
    @Schema(description = "status code")
    public int status;
}


