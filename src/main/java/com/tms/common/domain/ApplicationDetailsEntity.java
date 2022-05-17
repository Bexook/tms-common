package com.tms.common.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "application_details", schema = "service")
public class ApplicationDetailsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "app_key")
    private String appKey;
    @Column(name = "app_secret")
    private String appSecret;
    @Column(name = "active")
    private boolean active;
    @Column(name = "route")
    private String route;
    @Column(name = "created")
    private LocalDate created;
    @Column(name = "updated")
    private LocalDate updated;
    @Column(name = "created_by")
    private String createdBy;
}
