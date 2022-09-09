package com.example.notesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
// класс помощник для работы с базой данных
public class NotesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";
    private static final int DB_VVERSION = 1;


    public NotesDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VVERSION);
    }

    //вызывается при создании таблицы в базе данных в качестве параметра база данных
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // что бы вызвать SQLзапрос (в качестве параметра создание запроса)
        sqLiteDatabase.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);
    }

    //    если база данных обновилась необходимо удалить старую базу данных и создать новую
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(NotesContract.NotesEntry.DROP_COMMAND);
        onCreate(sqLiteDatabase);
    }
}
