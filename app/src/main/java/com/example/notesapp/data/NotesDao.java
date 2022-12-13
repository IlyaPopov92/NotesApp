package com.example.notesapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notesapp.pojo.Note;

import java.util.List;

//Dao - Data access object (объект доступа к данным)
@Dao
public interface NotesDao {

    //метод который возвращает все данные из БД при запросе БДи сортирует по дням неделям
    //При добавлении или удалении записей что бы база данных сама это понимала и меняла в списке -  LiveData
    @Query("SELECT * FROM notes ORDER BY priority ASC")
    LiveData<List<Note>> getAllNotes();

    //метод вставляет в базу данных данные
    @Insert
    void InsertNote(Note note);

    //метод удаляет заметки
    @Delete
    void deleteNote(Note note);
    //метод удаляет все заметки
    @Query("DELETE FROM notes")
    void deleteAllNote();
}
