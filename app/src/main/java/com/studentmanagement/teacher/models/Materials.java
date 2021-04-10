package com.studentmanagement.teacher.models;

public class Materials {

    String material_key, subject_name, unit, url;

    public Materials() {
    }

    public Materials(String material_key, String subject_name, String unit, String url) {
        this.material_key = material_key;
        this.subject_name = subject_name;
        this.unit = unit;
        this.url = url;
    }

    public String getMaterial_key() {
        return material_key;
    }

    public void setMaterial_key(String material_key) {
        this.material_key = material_key;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
