package com.by.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by yagamai on 16-3-15.
 */
@Entity
@Table(name = "by_batch_member_inc")
public class MemberIncPerDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_time")
    private Calendar createdTime;

    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar time) {
        this.createdTime = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberIncPerDay that = (MemberIncPerDay) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
