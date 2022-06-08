package com.tms.common.domain;


import com.tms.common.domain.enumTypes.CalendarDayType;
import com.tms.common.changeRequestDomain.entityMarker.ChangeRequestEntityMarker;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "fetchByTimeSpan", query = "SELECT * FROM time_log WHERE date >= :startDate AND date <= :endDate"),
        @NamedNativeQuery(name = "fetchByUserAndTimeSpan", query = "SELECT * FROM time_log WHERE date >= :startDate AND date <= :endDate AND created_by = :createdBy", resultClass = TimeLogEntity.class),
        @NamedNativeQuery(name = "fetchByUserList", query = "SELECT * FROM time_log WHERE created_by IN(:teamMembers)")})

@Data
@Entity
@Table(name = "time_log", schema = "tms")
@EqualsAndHashCode(callSuper = true)
public class TimeLogEntity extends BaseEntity implements ChangeRequestEntityMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "working_time")
    private Double timeSpend;

    @Column(name = "calendar_day_type")
    private CalendarDayType calendarDayType;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private UserEntity createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
