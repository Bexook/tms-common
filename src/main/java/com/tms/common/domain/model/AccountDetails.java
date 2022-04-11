package com.tms.common.domain.model;

import com.tms.common.domain.TeamMemberEntity;
import com.tms.common.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {

    private UserEntity userEntity;
    private TeamMemberEntity teamMemberEntity;
}
