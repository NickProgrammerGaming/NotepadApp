package janes.romanes.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity {

    public EditText titleEt;
    public EditText noteEt;

    int element;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("Edit note");
        setSupportActionBar(myToolbar);

        titleEt = findViewById(R.id.titleEt);
        noteEt = findViewById(R.id.noteEt);

        titleEt.setText(getIntent().getExtras().getString("Title"));
        noteEt.setText(getIntent().getExtras().getString("Note"));
        element = getIntent().getExtras().getInt("Element");

        sp = getSharedPreferences("MyNotes", MODE_PRIVATE);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.saveNote:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveNote()
    {
        SharedPreferences.Editor editor = sp.edit();

        if(element == -1)
        {
            editor.putString("title" + MainActivity.noteCount, titleEt.getText().toString());
            editor.putString("note" + MainActivity.noteCount, noteEt.getText().toString());
            MainActivity.noteCount++;
            editor.putInt("NoteCount", MainActivity.noteCount);
        }
        else
        {
            editor.putString("title" + element, titleEt.getText().toString());
            editor.putString("note" + element, noteEt.getText().toString());
        }

        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}