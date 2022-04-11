package com.tms.common.domain.enumTypes;

import com.tms.common.domain.TeamMemberEntity;

import java.util.Arrays;

public enum SystemDomainType {

    TEAM_MEMBER(TeamMemberEntity.class, "Team member");


    private Class<?> domainClass;
    private String humanReadable;

    SystemDomainType(Class<?> domainClass, String humanReadable) {
        this.domainClass = domainClass;
        this.humanReadable = humanReadable;
    }


    public SystemDomainType getByDomainClass(final Class<?> domainClass) {
        return Arrays.stream(SystemDomainType.values())
                .filter(dType -> dType.getDomainClass().equals(domainClass))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


    public Class<?> getDomainClass() {
        return domainClass;
    }

    public String getHumanReadable() {
        return humanReadable;
    }
}
