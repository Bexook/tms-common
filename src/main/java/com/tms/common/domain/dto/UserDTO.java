package com.tms.common.domain.dto;

import com.tms.common.domain.enumTypes.auth.UserRole;
import com.tms.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseEntity {

    private Long id;
    private String email;
    private String password;
    private boolean isActive;
    private boolean isEnable;
    private boolean isExpired;
    private UserRole userRole;
    private boolean isCredentialsExpired;
    private boolean isEmailVerified;

}
