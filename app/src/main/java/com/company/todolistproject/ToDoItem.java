package com.company.todolistproject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

public class ToDoItem implements Serializable {
    private int id;
    private String date;
    private String text;
    private Boolean isDeleted;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    ToDoItem(int id, String date, String text, Boolean isDeleted) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.isDeleted = isDeleted;
    }
}
