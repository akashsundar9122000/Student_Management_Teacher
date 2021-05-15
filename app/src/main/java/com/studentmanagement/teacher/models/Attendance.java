package com.studentmanagement.teacher.models;

public class Attendance {
    private String student_id,first_name,last_name,roll_no;
    private boolean present;


    public Attendance() {
    }

    public Attendance(String student_id, String first_name, String last_name, String roll_no, boolean present) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.roll_no = roll_no;
        this.present = present;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
