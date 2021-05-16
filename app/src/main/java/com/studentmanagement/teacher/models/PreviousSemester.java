package com.studentmanagement.teacher.models;

public class PreviousSemester {
    String previoussemester_key, semester_name, url, user_id;

    public PreviousSemester() {

    }

    public PreviousSemester(String previoussemester_key, String semester_name, String url, String user_id) {
        this.previoussemester_key = previoussemester_key;
        this.semester_name = semester_name;
        this.url = url;
        this.user_id = user_id;
    }

    public String getPrevioussemester_key() {
        return previoussemester_key;
    }

    public void setPrevioussemester_key(String previoussemester_key) {
        this.previoussemester_key = previoussemester_key;
    }

    public String getSemester_name() {
        return semester_name;
    }

    public void setSemester_name(String semester_name) {
        this.semester_name = semester_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}


