package com.tms.common.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApplicationLoginDTO {
    private String appKey;
    private String appSecret;

}
