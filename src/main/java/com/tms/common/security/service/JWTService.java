package com.tms.common.security.service;


import com.tms.common.domain.enumTypes.auth.Authority;
import com.tms.common.domain.enumTypes.auth.UserRole;
import com.tms.common.domain.model.UserCredentials;
import com.tms.common.userAuthDataConfiguration.AppUserDetails;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface JWTService {

    String generateToken(AppUserDetails appUserDetails);

    void logout(HttpServletRequest httpServletRequest) throws AuthenticationException;

    String login(UserCredentials userCredentials) throws AuthenticationException;

    boolean isValid(String token);

    String getTokenFromRequest(HttpServletRequest httpServletRequest) throws AuthenticationException;

    String getTokenFromRequest(ServerHttpRequest serverHttpRequest) throws AuthenticationException;

    String getPrincipal(String token);

    Date getExpiration(String token);

}
