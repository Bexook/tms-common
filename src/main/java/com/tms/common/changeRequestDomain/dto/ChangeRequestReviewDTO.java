package com.tms.common.changeRequestDomain.dto;

import com.tms.common.changeRequestDomain.entity.ChangeRequestCommentEntity;
import com.tms.common.changeRequestDomain.entity.ChangeRequestEntity;
import com.tms.common.changeRequestDomain.enumTypes.ChangeRequestState;
import lombok.Data;

import java.util.List;

@Data
public class ChangeRequestReviewDTO {

    private Long approverId;
    private Long createdBy;
    private Long modifiedBy;
    private ChangeRequestEntity changeRequestEntity;
    private List<ChangeRequestCommentEntity> comment;
    private ChangeRequestState currentState;

}
