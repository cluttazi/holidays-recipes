package com.chrisluttazi.holidaysrecipes.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Holiday {

    @Id
    @GeneratedValue
    private Long holidayId;

    @DateTimeFormat(iso = ISO.DATE)
    private Date date;

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 40)
    private String title;

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 40)
    private String description;

    public Holiday() {
    }

    public Holiday(Long holidayId, Date date, String title, String description) {
        this.holidayId = holidayId;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public Long getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(Long holidayId) {
        this.holidayId = holidayId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return title;
    }
}
