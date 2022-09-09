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
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //инициализируем объект MainViewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerViewNotes = findViewById(R.id.RecycleViewNotes);
        getData();

        adapter = new NotesAdapter(notes);
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(Note note) {
                remove(note.getId());
                Toast.makeText(MainActivity.this, "Вы удалили заметку", Toast.LENGTH_SHORT).show();
            }
        });

        // что бы пользоваться свайпом, анонимный класс SimpleCallback, переопределяем два метода, в параметре 0 и в какую сторону свайпать
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            //onMove вызывается когда хотим переместить один элемент на место другого
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                 int direction
            ) {
                // direction - передает номер напрвления движения
                remove(viewHolder.getAdapterPosition());
                Toast.makeText(MainActivity.this, "Вы удалили заметку",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // передаем recycle в свайп с помощью данного метода.
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

    }

    private void remove(int position) {
        //получаем экземпляр записки из адаптера
        Note note = adapter.getNotes().get(position);
        viewModel.deleteNote(note);
    }

    public void OnClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
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

}