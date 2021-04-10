package com.studentmanagement.teacher.models;

public class Materials {

    String material_key, material, url;

    public Materials() {
    }

    public Materials(String material_key, String material, String url) {
        this.material_key = material_key;
        this.material = material;
        this.url = url;
    }

    public String getMaterial_key() {
        return material_key;
    }

    public void setMaterial_key(String material_key) {
        this.material_key = material_key;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
