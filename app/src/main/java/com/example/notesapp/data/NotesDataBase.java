package com.example.notesapp.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.notesapp.pojo.Note;

//класс создан помечаем аннотацией(таблицы передаем различнве таблицы, версия БД)
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDataBase extends RoomDatabase {
    //паттерн SINGLETTON - что бы всегда быть уверен что объект класса существует только один
    private static NotesDataBase instance;

    private static final String DB_NAME = "notes2.db";

    //необходимо добавить объект для синхронизации
    private static final Object LOCK = new Object();

    public static NotesDataBase getInstance(Context context) {
        //бывает доступ к базе данных из разных потоков, эти потоки необходимо добавить в блок синхронизации,
        // когда метод занят одним потоком второй поток ждет
        synchronized (LOCK) {
            if (instance == null) {
                // что бы создать объект базы данных используем строитель
                instance = Room.databaseBuilder(context, NotesDataBase.class, DB_NAME).build();
            }
        }
        return instance;
    }

    public abstract NotesDao notesDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }
    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
    @Override
    public void clearAllTables() {

    }


}
