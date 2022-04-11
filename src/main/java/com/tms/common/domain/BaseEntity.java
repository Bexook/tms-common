package com.tms.common.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Data
@Transactional
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


    @PrePersist
    public void initPersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void initUpdate() {
        modifiedAt = LocalDateTime.now();
    }


}
