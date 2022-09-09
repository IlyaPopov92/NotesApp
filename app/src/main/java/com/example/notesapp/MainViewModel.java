package com.example.notesapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp.data.NotesDataBase;
import com.example.notesapp.pojo.Note;

import java.util.List;


//Через данный класс делаем запрос к БД запуская в другом програмном потоке (есть свой жизненный цикл)
public class MainViewModel extends AndroidViewModel {
    private static NotesDataBase dataBase;

    //будем хранить все заметки
    private LiveData<List<Note>> notes;


    public MainViewModel(@NonNull Application application) {
        super(application);
        //получаем значения для БД

        dataBase = NotesDataBase.getInstance(getApplication());
        //notesDao() интрефейс для работы с базой
        //получаем все заметки из БД
        notes = dataBase.notesDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    //создаем метод для вствки в заметки в другом потоке в БД, так же используем AsyncTask
    public void insertNote(Note note) {
        new InsertTask().execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteTask().execute(note);
    }

    public void deleteAllNote() {
        new DeleteAllTask().execute();
    }

    public static class InsertTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                dataBase.notesDao().InsertNote(notes[0]);
            }
            return null;
        }
    }

    public static class DeleteTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                //удаляем из базы данных
                dataBase.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    public static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... notes) {
            dataBase.notesDao().deleteAllNote();
            return null;
        }
    }
}


