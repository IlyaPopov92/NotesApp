package com.example.notesapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.notesapp.pojo.Note;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTittle;
    private EditText editTextDescription;
    private Spinner spinnerDayOfWeek;
    private RadioGroup radioGroupPriority;

    /*private NotesDBHelper dbHelper;
    private SQLiteDatabase database;*/
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
       /* dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();*/
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        editTextTittle = findViewById(R.id.editTextTittle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

    }

    public void OnClickSaveNote(View view) {
        //метод trim() - избавиться от лишних пробелов
        String title = editTextTittle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
        int radioButtonID = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonID);
        int priority = Integer.parseInt(radioButton.getText().toString());
        if (isFilled(title, description)){
            Note note = new Note(title,description,dayOfWeek,priority);
           viewModel.insertNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_fields, Toast.LENGTH_SHORT).show();
        }
       /* if (IsFilled(title, description)) {
            // что бы вставить новую запись в базу данных, кладем значения парами ключ-значение
            ContentValues values = new ContentValues();
            values.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            values.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            values.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, dayOfWeek);
            values.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            //values вставляем в базу данных
//            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, values);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_fields, Toast.LENGTH_SHORT).show();
        }*/
    }

    private boolean isFilled (String title, String description){
        return !title.isEmpty() && !description.isEmpty();
    }
}