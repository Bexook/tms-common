package com.tms.common.domain.converter;

import com.tms.common.domain.enumTypes.TeamMemberRole;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Component
@Converter
public class TeamMemberRoleConverter implements AttributeConverter<TeamMemberRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TeamMemberRole teamMemberRole) {
        return teamMemberRole.getCode();
    }

    @Override
    public TeamMemberRole convertToEntityAttribute(Integer integer) {
        return TeamMemberRole.getByCode(integer);
    }
}