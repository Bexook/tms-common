package com.tms.common.annotation;

import com.tms.common.changeRequestDomain.entityMarker.ChangeRequestEntityMarker;
import com.tms.common.domain.enumTypes.auth.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Approver {

    Class<?> repository();

    Class<? extends ChangeRequestEntityMarker> domainClass();

    UserRole[] userRoles() default UserRole.ADMIN;
}
