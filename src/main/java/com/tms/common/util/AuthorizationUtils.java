package com.tms.common.util;

import com.tms.common.userAuthDataConfiguration.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationUtils {

    public static String getCurrentUsername() {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public static Long getCurrentAuthenticatedUserId() {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUserId();
    }


}
