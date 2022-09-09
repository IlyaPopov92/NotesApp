package com.example.notesapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Что бы могли хранить объект в базе данных помечаем аннотацией (в круглых скобках пише название таблицы)
@Entity (tableName = "notes")
public class Note {
    //каждая таблица содержит уникальный ID
    //помечаетя аннотоцией что это и есть первичный ключ и генерируется автоматически
    //должен содержать конструктор,гетеры и сеттеры, что бы можно было использовать в базе данных

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String dayOfWeek;
    private int priority;

    public Note(int id, String title, String description, String dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    @Ignore
    public Note(String title, String description, String dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
