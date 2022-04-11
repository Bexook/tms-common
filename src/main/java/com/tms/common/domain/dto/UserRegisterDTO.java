package com.tms.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tms.common.domain.enumTypes.TeamMemberRole;
import lombok.Data;

@Data
public class UserRegisterDTO {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("repeatPassword")
    private String repeatPassword;
    @JsonProperty("email")
    private String email;
    @JsonProperty("teamMemberRole")
    private TeamMemberRole teamMemberRole;

}
