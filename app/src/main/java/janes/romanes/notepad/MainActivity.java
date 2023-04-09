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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("Notepad");
        setSupportActionBar(myToolbar);
        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        boolean nightMode = sharedPreferences.getBoolean("night", false);

        if(nightMode)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        notesView = findViewById(R.id.notesList);

        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteTitles);
        loadNotes();
        notesView.setAdapter(notesAdapter);
        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent newNote = new Intent(MainActivity.this, NewNoteActivity.class);
                newNote.putExtra("Title", notes.get(i).getTitle());
                newNote.putExtra("Note", notes.get(i).getNote());
                newNote.putExtra("Element", i);
                startActivity(newNote);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.newNote:
                Intent newNote = new Intent(this, NewNoteActivity.class);
                newNote.putExtra("Title", "");
                newNote.putExtra("Note", "");
                newNote.putExtra("Element", -1);
                startActivity(newNote);
                return true;
            case R.id.settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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

}