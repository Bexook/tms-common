package com.tms.common.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ApplicationDetailsDTO {
    private String appName;
    private String appKey;
    private String appSecret;
    private boolean active;
    private String route;
    private LocalDate created;
    private LocalDate updated;
    private String created_by;
}
