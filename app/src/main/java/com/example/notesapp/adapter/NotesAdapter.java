package com.example.notesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.pojo.Note;
import com.example.notesapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    public List<Note> notes;
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }


    //  создаем интерфейс слушателя событий
    public interface OnNoteClickListener {
        void onNoteClick(int position);
        //увеличиваем время нажатия по клику что бы удалялось не сразу
        void onLongClick(int position);
    }

    // метод который создает макет
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    //метод который наполняет макет данными
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayOfWeek.setText(note.getDayOfWeek());

        int color;
        int priorityId = note.getPriority();
        switch (priorityId) {
            case 1:
                color = android.R.color.holo_red_light;
                break;
            case 2:
                color = android.R.color.holo_orange_light;
                break;
            default:
                color = android.R.color.holo_green_light;
                break;
        }
        int color1 = ContextCompat.getColor(holder.itemView.getContext(),color);
        holder.textViewTitle.setBackgroundColor(color1);

    }

    // метод просто хранит размер
    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Данный класс создан для передачи данных во ViewHolder
    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewDayOfWeek;


        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);

            //Вызываем слушателя и переопределяем метод и по адаптеру устанавливаем позицию для слушателя
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteClickListener != null) {
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            // добавляем слушателя для долгого нажатия на кнопку
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onNoteClickListener != null){
                        onNoteClickListener.onLongClick(getAdapterPosition());
                    }

                    return true;
                }
            });

        }
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return notes;
    }
}
