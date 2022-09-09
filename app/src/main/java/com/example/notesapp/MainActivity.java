package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.adapter.NotesAdapter;
import com.example.notesapp.pojo.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
     /*private NotesDBHelper dbHelper;
    private SQLiteDatabase database;*/
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //инициализируем объект MainViewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerViewNotes = findViewById(R.id.RecycleViewNotes);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
       /* dbHelper = new NotesDBHelper(this);
        // получаем базу данных для записи данных
        database = dbHelper.getWritableDatabase();
        getData();*/
        adapter = new NotesAdapter(notes);
        getData();
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
                Toast.makeText(MainActivity.this, "Вы удалили заметку", Toast.LENGTH_SHORT).show();
            }
        });
        // что бы пользоваться свайпом, анонимный класс Simple, переопределяем два метода, в параметре 0 и в какую сторону свайпать
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // direction - передает номер напрвления движения
                remove(viewHolder.getAdapterPosition());
                Toast.makeText(MainActivity.this, "Вы удалили заметку", Toast.LENGTH_SHORT).show();
            }
        });
        // передаем recycle в свайп с помощью данного метода.
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

    }

    private void remove(int position) {
       /* int id = notes.get(position).getId();
        // удаление данных из базы данных, параметры (имя табоицы, where - откуда удаляем "указываем параметры удаления, знак вопроса показывает что удаляем",whereArgs -  что именно удаляем)
        String where = NotesContract.NotesEntry._ID + " = ? ";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);
        getData();*/
        //получаем экземпляр записки из адаптера
        Note note = adapter.getNotes().get(position);
        viewModel.deleteNote(note);
    }

    public void OnClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData(){
        //notesFromDB - является просматриваемым observible если произойдут изменнеия база данных об этом сообщит
        LiveData<List<Note>> notesFromDB = viewModel.getNotes();
        //если произойдут изменнеия база данных об этом сообщит - два параметра владелец - owner, анонимный класс Observer
        notesFromDB.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesLiveData) {
                adapter.setNotes(notesLiveData);
            }
        });
    }
    /*private void getData() {
        notes.clear();
        //вытаскиваем данные из базы данных используем объект  Cursor - хранятся все данные из базы данных указывает на конкретные строки
        //orderBy-этим атрибутом можем отсортировать таблицу в БД по колонке
        //selection и selectionArgs, работают так же как и  where, whereArgs
        String selection = NotesContract.NotesEntry.COLUMN_PRIORITY + " < ? ";
        String[] selectionArgs = new String[]{"2"};
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, selection, selectionArgs, null, null, NotesContract.NotesEntry.COLUMN_PRIORITY);
       получаем конкретную строку moveToNext() - возвращает значением boolean, если есть какие то записи приведет к строке с индексом 0
        и с помощью цикла while прочитаем все строки
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(id, title, description, dayOfWeek, priority);
            notes.add(note);
        }
        cursor.close();
    }*/
}