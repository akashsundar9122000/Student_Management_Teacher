package com.studentmanagement.teacher.models;

public class TimeTable {

    String day,period,subject_code,staff_name;
    boolean isBreak;

    public TimeTable() {

    }

    public TimeTable(String day, String period, String subject_code, String staff_name, boolean isBreak) {
        this.day = day;
        this.period = period;
        this.subject_code = subject_code;
        this.staff_name = staff_name;
        this.isBreak = isBreak;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setBreak(boolean aBreak) {
        isBreak = aBreak;
    }
}
