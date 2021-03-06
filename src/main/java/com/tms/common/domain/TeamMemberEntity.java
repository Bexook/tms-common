package com.tms.common.domain;


import com.tms.common.domain.dto.TeamMemberDTO;
import com.tms.common.domain.enumTypes.TeamMemberRole;
import com.tms.common.changeRequestDomain.entityMarker.ChangeRequestEntityMarker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.*;

@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "toTeamMemberDTO",
                classes =
                @ConstructorResult(targetClass = TeamMemberDTO.class, columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "username"),
                        @ColumnResult(name = "team_member_role", type = Integer.class),
                        @ColumnResult(name = "user_id", type = Long.class)
                }))
})

@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "findDTOById",
                query = "SELECT * FROM team_member WHERE team_member.id=:id",
                resultClass = TeamMemberDTO.class,
                resultSetMapping = "toTeamMemberDTO"
        ),
        @NamedNativeQuery(name = "findDTOByUserId",
                query = "SELECT * FROM team_member WHERE team_member.user_id=:id",
                resultClass = TeamMemberDTO.class,
                resultSetMapping = "toTeamMemberDTO"
        )
})

@Data
@Entity
@ToString
@Table(name = "team_member", schema = "tms")
@Filter(name = "filterByRole", condition = "team_member_role = :role")
@FilterDef(name = "filterByRoleDef", parameters = @ParamDef(name = "role", type = "String"))
@SQLDelete(sql = "UPDATE app_user SET is_active = 0 WHERE public.app_user.id= ? ", check = ResultCheckStyle.COUNT)
public class TeamMemberEntity implements ChangeRequestEntityMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String name;

    @Column(name = "team_member_role")
    private TeamMemberRole memberRole;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            columnDefinition = "BIGINT NOT NULL UNIQUE",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_id_constraint"))
    private UserEntity user;

}
