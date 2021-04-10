package com.studentmanagement.teacher.models;

public class Notes {
    String note_key, note, url;

    public Notes() {
    }

    public Notes(String note_key, String note, String url) {
        this.note_key = note_key;
        this.note = note;
        this.url = url;
    }

    public String getNote_key() {
        return note_key;
    }

    public void setNote_key(String note_key) {
        this.note_key = note_key;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
