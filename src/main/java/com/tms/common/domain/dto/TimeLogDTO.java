package com.tms.common.domain.dto;


import com.tms.common.domain.UserEntity;
import com.tms.common.domain.enumTypes.CalendarDayType;
import com.tms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimeLogDTO extends BaseEntity {

    private LocalDate date;
    private Double timeSpend;
    private CalendarDayType calendarDayType;
    private UserEntity userEntity;

}
