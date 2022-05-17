package com.tms.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "active_tokens", schema = "tms")
public class JWTTokenEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "jwt_token")
    private String jwtToken;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private UserEntity user;

    public JWTTokenEntity(String jwtToken, final UserEntity user) {
        this.jwtToken = jwtToken;
        this.user = user;
    }
}
