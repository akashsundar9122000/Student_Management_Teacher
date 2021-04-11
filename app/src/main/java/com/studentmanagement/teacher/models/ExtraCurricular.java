package com.studentmanagement.teacher.models;

public class ExtraCurricular {
    String extracurricular_key, activity,organisation, url, user_id;

    public ExtraCurricular() {

    }

    public ExtraCurricular(String extracurricular_key, String activity, String organisation, String url, String user_id) {
        this.extracurricular_key = extracurricular_key;
        this.activity = activity;
        this.organisation = organisation;
        this.url = url;
        this.user_id = user_id;
    }

    public String getExtracurricular_key() {
        return extracurricular_key;
    }

    public void setExtracurricular_key(String extracurricular_key) {
        this.extracurricular_key = extracurricular_key;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
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
