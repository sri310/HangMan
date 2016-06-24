package com.example.admin.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText namebox;
    TextView hscore;
    int highscore;
    String topscorer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namebox = (EditText)findViewById(R.id.editText);
        hscore=(TextView)findViewById(R.id.textView11);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highscore = prefs.getInt("key", 0);
        topscorer = prefs.getString("topper", "User");
        hscore.setText(topscorer + " - " + String.valueOf(highscore));
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highscore = prefs.getInt("key", 0);
        topscorer = prefs.getString("topper", null);
        hscore.setText(topscorer + " - " + String.valueOf(highscore));

    }

    public void animals(View view) {
        Intent intent  = new Intent(this,gameo.class);
        String name = namebox.getText().toString();
        intent.putExtra("intVariableName", 0);
        intent.putExtra("Name", name);
        startActivity(intent);

    }

    public void geography(View view) {
        Intent intent  = new Intent(this,gameo.class);
        String name = namebox.getText().toString();
        intent.putExtra("intVariableName", 1);
        intent.putExtra("Name", name);
        startActivity(intent);

    }

    public void food(View view) {
        Intent intent  = new Intent(this,gameo.class);
        String name = namebox.getText().toString();
        intent.putExtra("intVariableName", 2);
        intent.putExtra("Name", name);
        startActivity(intent);
    }

    public void anatomy(View view) {
        Intent intent  = new Intent(this,gameo.class);
        String name = namebox.getText().toString();
        intent.putExtra("intVariableName", 3);
        intent.putExtra("Name", name);
        startActivity(intent);

    }

}
