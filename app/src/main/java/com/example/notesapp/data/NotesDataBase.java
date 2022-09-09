package com.example.notesapp.data;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.notesapp.pojo.Note;

//класс создан помечаем аннотацией(таблицы передаем которые являются таблицами в БД, версия БД)
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDataBase extends RoomDatabase {
    //паттерн SINGLETTON - что бы всегда быть уверен что объект класса существует только один
    private static NotesDataBase instance = null;

    private static final String DB_NAME = "notes2.db";

    //необходимо добавить объект для синхронизации
    private static final Object LOCK = new Object();

    public static NotesDataBase getInstance(Application application) {
        //бывает доступ к базе данных из разных потоков, эти потоки необходимо добавить в блок синхронизации,
        // когда метод занят одним потоком второй поток ждет
        //Application являетсяяя всем приложением и наслдеуется от контекста
        synchronized (LOCK) {
            if (instance == null) {
                // что бы создать объект базы данных используем строитель
                instance = Room.databaseBuilder(
                        application,
                        NotesDataBase.class,
                        DB_NAME).build();
            }
        }
        return instance;
    }

    public abstract NotesDao notesDao();
}
