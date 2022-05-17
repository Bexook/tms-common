package com.tms.common.repository;

import com.tms.common.domain.ApplicationDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRegistryRepository extends JpaRepository<ApplicationDetailsEntity, Long> {

    ApplicationDetailsEntity getByAppKey(final String appKey);

    void deleteByAppKey(final String appKey);


}
