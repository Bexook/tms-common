package com.tms.common.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDTO {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedA;
}