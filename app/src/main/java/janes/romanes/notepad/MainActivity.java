package janes.romanes.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView toolbarTitle;
    ListView notesView;

    public static int noteCount;
    public static ArrayList<String> noteTitles = new ArrayList<>();
    ArrayAdapter<String> notesAdapter;
    private ArrayList<Note> notes = new ArrayList<>();

    SharedPreferences sharedPreferences;

    ImageView addNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        addNote = findViewById(R.id.addNote);

        notesView = findViewById(R.id.notesList);

        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteTitles);
        loadNotes();
        notesView.setAdapter(notesAdapter);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote("", "", -1);
            }
        });
        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addNewNote(notes.get(i).getTitle(), notes.get(i).getNote(), i);
            }
        });

    }

    private void loadNotes()
    {
        SharedPreferences sharedPref = getSharedPreferences("MyNotes", MODE_PRIVATE);
        noteCount = sharedPref.getInt("NoteCount", 0);
        noteTitles.clear();
        notes.clear();
        // Load the notes
        String tempTitle = "";
        String tempNote = "";
        for(int i = 0; i < noteCount; i++)
        {
            tempTitle = sharedPref.getString("title" + i, "");
            tempNote = sharedPref.getString("note" + i, "");
            Note n = new Note(tempTitle, tempNote);
            noteTitles.add(tempTitle);
            notes.add(n);
        }
        notesAdapter.notifyDataSetChanged();
    }

    private void addNewNote(String title, String note, int element)
    {
        Intent newNote = new Intent(this, NewNoteActivity.class);
        newNote.putExtra("Title", title);
        newNote.putExtra("Note", note);
        newNote.putExtra("Element", element);
        startActivity(newNote);
    }

}