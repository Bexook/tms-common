package com.tms.common.security.service.impl;

import com.tms.common.domain.JWTTokenEntity;
import com.tms.common.domain.UserEntity;
import com.tms.common.domain.dto.UserDTO;
import com.tms.common.domain.enumTypes.auth.Authority;
import com.tms.common.domain.enumTypes.auth.UserRole;
import com.tms.common.domain.model.UserCredentials;
import com.tms.common.mapper.OrikaBeanMapper;
import com.tms.common.repository.JWTTokenRepository;
import com.tms.common.security.AppUserDetailsService;
import com.tms.common.security.service.JWTService;
import com.tms.common.security.service.UserService;
import com.tms.common.userAuthDataConfiguration.AppUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
public class JWTServiceImpl implements JWTService {

    private final String AUTHORIZATION = "Authorization";

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private int expirationPeriod;


    @Autowired
    private OrikaBeanMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTokenRepository jwtTokenRepository;
    @Autowired
    private AppUserDetailsService appUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String generateToken(AppUserDetails appUserDetails) {
        final UserDTO user = userService.getUserById(appUserDetails.getUserId());
        Date expiration = Date.from(Instant.from(LocalDate.now().plusDays(expirationPeriod).atStartOfDay(ZoneId.systemDefault())));
        Claims claims = Jwts.claims().setSubject(appUserDetails.getUsername());
        claims.put("userRole", appUserDetails.getAuthorities());
        claims.setExpiration(expiration);
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        jwtTokenRepository.save(new JWTTokenEntity(token, mapper.map(user, UserEntity.class)));
        return token;
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) throws AuthenticationException {
        String token = getTokenFromRequest(httpServletRequest);
        jwtTokenRepository.deleteByJwtToken(token);
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }

    @Override
    public String login(UserCredentials creds) throws AuthenticationException {
        AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(creds.getLogin());
        if (Objects.nonNull(userDetails) && userDetails.nonNullProperties() && passwordEncoder.matches(creds.getPassword(), userDetails.getPassword())) {
            return generateToken(userDetails);
        }
        throw new AuthenticationException("Unknown user");
    }

    @Override
    public boolean isValid(String token) {
        JWTTokenEntity tokenFromDB = jwtTokenRepository.findByJwtToken(token);
        return Objects.nonNull(tokenFromDB) && new Date(System.currentTimeMillis()).before(getExpiration(token));

    }

    @Override
    public String getTokenFromRequest(HttpServletRequest httpServletRequest) throws AuthenticationException {
        String token = httpServletRequest.getHeader(AUTHORIZATION);
        if (Objects.nonNull(token) && Strings.isNotBlank(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new AuthenticationException("Bearer token not found");
    }

    @Override
    public String getPrincipal(String token) {
        return (String) getClaims(token).getSubject();
    }


    @Override
    public String getTokenFromRequest(ServerHttpRequest serverHttpRequest) throws AuthenticationException {
        String token = serverHttpRequest.getHeaders().get(AUTHORIZATION).stream().filter(header -> header.startsWith("Bearer ")).findFirst().orElseThrow(() -> new AuthenticationException("Bearer token not found"));
        if (Objects.nonNull(token.substring(7)) && Strings.isNotBlank(token.substring(7))) {
            return token.substring(7);
        }
        throw new AuthenticationException("Bearer token not found");
    }

    @Override
    public Authority getAuthority(String token) {
        return getClaims(token).get("authority", Authority.class);
    }

    @Override
    public UserRole getUserRole(String token) {
        return UserRole.valueOf(getClaims(token).get("userRole", String.class));
    }

    @Override
    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
