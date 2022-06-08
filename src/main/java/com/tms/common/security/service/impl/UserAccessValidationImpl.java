package com.tms.common.security.service.impl;

import com.tms.common.domain.enumTypes.auth.Authority;
import com.tms.common.domain.enumTypes.auth.UserRole;
import com.tms.common.userAuthDataConfiguration.AppUserDetails;
import com.tms.common.security.service.UserAccessValidation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.AccessType;
import java.util.List;
import java.util.stream.Collectors;

@Service("userAccessValidation")
public class UserAccessValidationImpl implements UserAccessValidation {

    @Override
    public boolean isAllowedAccessType(String... accessType) {
        AppUserDetails user = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AccessType> accessTypeList = List.of(accessType).stream().map(AccessType::valueOf).collect(Collectors.toList());
        return accessTypeList.contains(user.getAccessType());
    }

    @Override
    public boolean hasAuthority(String... authority) {
        AppUserDetails user = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Authority> authorityList = List.of(authority).stream().map(Authority::valueOf).collect(Collectors.toList());
        return user.getAuthorities().containsAll(authorityList);
    }

    @Override
    public boolean hasRole(String... userRole) {
        AppUserDetails user = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserRole> userRoleList = List.of(userRole).stream().map(UserRole::valueOf).collect(Collectors.toList());
        return userRoleList.contains(user.getUserRole());
    }
}
