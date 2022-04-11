package com.tms.common.repository;


import com.tms.common.domain.JWTTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTTokenRepository extends JpaRepository<JWTTokenEntity, Long> {


    JWTTokenEntity findByJwtToken(String token);

    void deleteByJwtToken(String token);


}
