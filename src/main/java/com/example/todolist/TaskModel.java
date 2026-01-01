package com.example.todolist;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TaskModel {

    private Long id;
    private String task;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public TaskModel () {

    }

    public TaskModel (long id, String task, Date date) {
        this.id = id;
        this.task = task;
        this.date = date;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
