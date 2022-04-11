package com.tms.common.changeRequestDomain.dto;


import com.tms.common.changeRequestDomain.enumTypes.OperationType;
import com.tms.common.domain.enumTypes.auth.UserRole;
import com.tms.common.changeRequestDomain.entity.ChangeRequestCommentEntity;
import com.tms.common.changeRequestDomain.enumTypes.ChangeRequestState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequestDTO {
    private Long id;
    private String createdBy;
    private String modifiedBy;
    private UserRole userRole;
    private ChangeRequestState changeRequestState;
    private OperationType operationType;
    private String currentObjectState;
    private String newObjectState;
    private String domainClass;
    private Set<ChangeRequestCommentEntity> changeRequestCommentEntities;
    private boolean relevant = true;
    private String objectRepo;
}
