package com.tms.common.changeRequestDomain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequestCommentDTO {

    private Long id;

    private String comment;

    private String commentBy;

    private ChangeRequestDTO changeRequest;

}
