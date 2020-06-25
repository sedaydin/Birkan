package com.example.notebook;

public class ContactDb {
    public String title,note,id;

    public ContactDb() {

    }

    public ContactDb(String title, String note, String id) {
        this.title = title;
        this.note = note;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteId() {
        return id;
    }

    public void setNoteId(String id) {
        this.id = id;
    }

}
