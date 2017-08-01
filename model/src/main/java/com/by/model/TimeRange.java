package com.by.model;

import java.util.Calendar;

/**
 * Created by yagamai on 16-1-28.
 */
public interface TimeRange extends Valid {
    Calendar getBeginTime();

    void setBeginTime(Calendar calendar);

    Calendar getEndTime();

    void setEndTime(Calendar calendar);

}
