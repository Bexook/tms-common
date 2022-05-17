package com.tms.common.security.filter;


import com.tms.common.domain.dto.ApplicationLoginDTO;
import com.tms.common.security.AppUserDetailsService;
import com.tms.common.security.service.AppRegistryService;
import com.tms.common.security.service.JWTService;
import com.tms.common.userAuthDataConfiguration.AppUserDetails;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class ServiceValidationFilter extends OncePerRequestFilter {

    @Autowired
    private AppRegistryService appRegistryService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Override
    @SneakyThrows
    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        if (request.getRequestURI().endsWith("/login") || request.getRequestURI().endsWith("/logout")) {
            filterChain.doFilter(request, response);
        } else {
            final ApplicationLoginDTO login = appRegistryService.getLoginFromRequest(request);
            if (appRegistryService.validateApplication(login)) {
                final String token = jwtService.getTokenFromRequest(request);
                AppUserDetails appUserDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(jwtService.getPrincipal(token));
                UsernamePasswordAuthenticationToken t =
                        new UsernamePasswordAuthenticationToken(appUserDetails, null, null);
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(t);
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        }
    }

}
