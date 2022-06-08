package com.tms.common.domain.dto;

import com.tms.common.domain.enumTypes.TeamMemberRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Data
@NoArgsConstructor
public class TeamMemberDTO {

    private Long id;
    private String name;
    private TeamMemberRole memberRole;
    private Long userId;
    private UserDTO user;
    private TeamDTO team;

    public TeamMemberDTO(Long id, String name, TeamMemberRole teamMemberRole, UserDTO user) {
            this.id = id;
            this.name = name;
            this.memberRole = teamMemberRole;
            this.user = user;
        }
    }
