package com.studentmanagement.teacher.models;

public class Links {

    String link_key, sub_name, time, link;

    public Links() {

    }

    public Links(String link_key, String sub_name, String time, String link) {
        this.link_key = link_key;
        this.sub_name = sub_name;
        this.time = time;
        this.link = link;
    }

    public String getLink_key() {
        return link_key;
    }

    public void setLink_key(String link_key) {
        this.link_key = link_key;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

