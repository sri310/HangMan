package com.example.admin.hangman;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class gameo extends AppCompatActivity {
    Random rn;
    String game_word;
    public static int lives = 8;
    public static int score = 0;
    public static int count = 0;
    InputStream ips = null;
    ArrayList<String> wordlist;
    ArrayList<String> finished = new ArrayList<>();
    HashSet<Character> foundletters = new HashSet<>();
    TextView gameView, liveView, scoreView,list,username;
    ImageView strike;
    Button buttons[] = new Button[26];
    Button guess;
    Button new_word;
    private int  New_word_count = 3;
    public int highscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameo);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highscore = prefs.getInt("key", 0);
        gameView = (TextView) findViewById(R.id.textView2);
        liveView = (TextView) findViewById(R.id.textView5);
        scoreView = (TextView) findViewById(R.id.textView6);
        strike = (ImageView) findViewById(R.id.hanger);
        guess=(Button)findViewById(R.id.button3);
        new_word=(Button)findViewById(R.id.button2);
        username = (TextView) findViewById(R.id.textView8);
        list = (TextView) findViewById(R.id.textView9);
        Bundle bundle = getIntent().getExtras();
        Intent mIntent = getIntent();
        int fileValue = mIntent.getIntExtra("intVariableName", 0);
        String name = bundle.getString("Name");
        if(name.equals(null))
        {
            name="User";
        }
        username.setText(name);
        buttons[0]=(Button)findViewById(R.id.A);
        buttons[1]=(Button)findViewById(R.id.B);
        buttons[2]=(Button)findViewById(R.id.C);
        buttons[3]=(Button)findViewById(R.id.D);
        buttons[4]=(Button)findViewById(R.id.E);
        buttons[5]=(Button)findViewById(R.id.F);
        buttons[6]=(Button)findViewById(R.id.G);
        buttons[7]=(Button)findViewById(R.id.H);
        buttons[8]=(Button)findViewById(R.id.I);
        buttons[9]=(Button)findViewById(R.id.J);
        buttons[10]=(Button)findViewById(R.id.K);
        buttons[11]=(Button)findViewById(R.id.L);
        buttons[12]=(Button)findViewById(R.id.M);
        buttons[13]=(Button)findViewById(R.id.N);
        buttons[14]=(Button)findViewById(R.id.O);
        buttons[15]=(Button)findViewById(R.id.P);
        buttons[16]=(Button)findViewById(R.id.Q);
        buttons[17]=(Button)findViewById(R.id.R);
        buttons[18]=(Button)findViewById(R.id.S);
        buttons[19]=(Button)findViewById(R.id.T);
        buttons[20]=(Button)findViewById(R.id.U);
        buttons[21]=(Button)findViewById(R.id.V);
        buttons[22]=(Button)findViewById(R.id.W);
        buttons[23]=(Button)findViewById(R.id.X);
        buttons[24]=(Button)findViewById(R.id.Y);
        buttons[25]=(Button)findViewById(R.id.Z);
        gameView.setText("");
        for(int i=0;i<26;i++)
        {
            buttons[i].setClickable(true);
            buttons[i].setBackgroundColor(Color.TRANSPARENT);
        }
        try {
            switch (fileValue)
            {
                case 0:
                    ips = getAssets().open("animal.txt");
                    list.setText("Animals");
                    break;
                case 1:
                    ips = getAssets().open("country.txt");
                    list.setText("Country");
                    break;
                case 2:
                    ips = getAssets().open("veg.txt");
                    list.setText("Vegies");
                    break;
                case 3:
                    ips = getAssets().open("anatomy.txt");
                    list.setText("Anatomy");
                    break;
                default:
                    ips = getAssets().open("animal.txt");
                    list.setText("Animals");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        wordlist = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(ips));
        String line;
        try {
            while ((line = in.readLine()) != null) {
                String word = line.trim();
                wordlist.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("on", String.valueOf(wordlist.size()));
        gen();
    }

    public void generate_new_word(View view) {

        New_word_count--;
        gen();
        if(New_word_count==0){
            new_word.setBackgroundColor(Color.parseColor("#D50000"));
            new_word.setClickable(false);

        }
    }

    public boolean check(char a) {
        int flag = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < game_word.length(); i++) {
            if (game_word.charAt(i) == a) {
                arr.add(i);
                count++;
                flag = 1;
            }
        }
        if (flag == 0) {
            int rid = getResources().getIdentifier("strike" + Integer.toString(8 - lives--), "drawable", getPackageName());
            final MediaPlayer letf= MediaPlayer.create(this, R.raw.letter_fail);
            strike.setImageResource(rid);
            letf.start();
            liveView.setText(String.valueOf(lives));
            if (lives == 0) {
                MediaPlayer levf = MediaPlayer.create(this, R.raw.level_fail);
                levf.start();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("You Loose");
                // set dialog message
                alertDialogBuilder
                        .setMessage("The word is " + game_word)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked,
                                //Intent intent = new Intent(this, MainActivity.class);
                                // startActivity(intent);
                                gameo.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }
        else {
            CharSequence set = null;
            set = gameView.getText();
            Log.d("on", String.valueOf(set.length()) + " " + gameView.getText());
            gameView.setText("");
            for (int i = 0; i < game_word.length(); i++) {
                if (!arr.contains(i)) {
                    gameView.append(String.valueOf(set.charAt(i)));
                } else {
                    gameView.append(String.valueOf(a));
                    foundletters.add(a);
                }
            }
        }

        if(flag==1)
        {
            return true;
        }
        return false;
    }

    public void checkwin()
    {
        if (count == game_word.length()) {
            final MediaPlayer levw= MediaPlayer.create(this, R.raw.level_win);
            levw.start();
            score++;
            scoreView.setText(String.valueOf(score));
            if(score>highscore)
            {
                SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("key", score);
                editor.putString("topper",username.getText().toString());
                editor.commit();
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("You Win!!!!");
            // set dialog message
            alertDialogBuilder
                    .setMessage("The word is " + game_word)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked,
                            //Intent intent = new Intent(this, MainActivity.class);
                            // startActivity(intent);
                            gen();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

        }
    }
    public void gen(){
        rn = new Random();
        lives = 8;
        count = 0;
        foundletters = new HashSet<>();
        liveView.setText(Integer.toString(lives));
        guess.setClickable(true);
        guess.setBackgroundColor(Color.parseColor("#009688"));
        for(int i=0;i<26;i++)
        {
            buttons[i].setClickable(true);
            buttons[i].setBackgroundColor(Color.TRANSPARENT);
        }
        int l = wordlist.size();
        int rid = getResources().getIdentifier("strike", "drawable", getPackageName());
        strike.setImageResource(rid);
        do {
            int index = rn.nextInt(l);
            game_word = wordlist.get(index);
        } while (finished.contains(game_word));
        finished.add(game_word);
        Log.d("on", game_word);
        int word_length = game_word.length();
        int i = word_length;
        gameView.setText("");
        while (i > 0) {
            gameView.append("*");
            i--;
        }

    }

    public void guess(View view) {
        guess.setBackgroundColor(Color.parseColor("#D50000"));
        guess.setClickable(false);
        CharSequence set = null;
        int flag;
        ArrayList<Integer> arr = new ArrayList<>();
        set = gameView.getText();
        HashMap<Character,Integer> game_guess = new HashMap<>();
        for (int i = 0; i < game_word.length(); i++) {
            if(!game_guess.containsKey(game_word.charAt(i))){
                if(!foundletters.contains(game_word.charAt(i)))
                    game_guess.put(game_word.charAt(i),0);
            }
            else{
                int count = game_guess.get(game_word.charAt(i));
                game_guess.put(game_word.charAt(i),count++);
            }

        }
        Log.d("on",String.valueOf(game_guess.size())+" size of hashmap");
        int min=999999;
        Character ch=null;
        for(Character c : game_guess.keySet()){
            Log.d("on","inside guess "+String.valueOf(c)+" looping");
            if(min>game_guess.get(c)){
                min=game_guess.get(c);
                //Log.d("on","inside guess"+String.valueOf(ch));
                ch = c;
            }
            Log.d("on","inside guess"+String.valueOf(ch));

        }
        check(ch);
        buttons[ch-'a'].setBackgroundColor(Color.parseColor("#EF6C00"));
        buttons[ch-'a'].setClickable(false);
        checkwin();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            //moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application? Your progress will be lost!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        gameo.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                })
                .show();
    }

    public void a(View view) {
        boolean presence= check('a');
        if(!presence)
        {
            buttons[0].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[0].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[0].setClickable(false);
        checkwin();
    }

    public void b(View view) {
        boolean presence=check('b');
        if(!presence)
        {
            buttons[1].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[1].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[1].setClickable(false);
        checkwin();
    }

    public void c(View view) {
        boolean presence= check('c');
        if(!presence)
        {
            buttons[2].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[2].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[2].setClickable(false);
        checkwin();
    }

    public void d(View view) {
        boolean presence=check('d');
        if(!presence)
        {
            buttons[3].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[3].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[3].setClickable(false);
        checkwin();
    }

    public void e(View view) {
        boolean presence=check('e');
        if(!presence)
        {
            buttons[4].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[4].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[4].setClickable(false);
        checkwin();
    }

    public void f(View view) {
        boolean presence= check('f');
        if(!presence)
        {
            buttons[5].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[5].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[5].setClickable(false);
        checkwin();}

    public void g(View view) {
        boolean presence= check('g');
        if(!presence)
        {
            buttons[6].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[6].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[6].setClickable(false);
        checkwin();}

    public void h(View view) {
        boolean presence=check('h');
        if(!presence)
        {
            buttons[7].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[7].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[7].setClickable(false);
        checkwin();}

    public void i(View view) {
        boolean presence= check('i');
        if(!presence)
        {
            buttons[8].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[8].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[8].setClickable(false);
        checkwin();}

    public void j(View view) {
        boolean presence=check('j');
        if(!presence)
        {
            buttons[9].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[9].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[9].setClickable(false);
        checkwin();}

    public void k(View view) {
        boolean presence= check('k');if(!presence)
        {
            buttons[10].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[10].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[10].setClickable(false);
        checkwin();
    }

    public void l(View view) {
        boolean presence= check('l');
        if(!presence)
        {
            buttons[11].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[11].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[11].setClickable(false);
        checkwin();
    }

    public void m(View view) {
        boolean presence=check('m');
        if(!presence)
        {
            buttons[12].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[12].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[12].setClickable(false);
        checkwin();
    }

    public void n(View view) {
        boolean presence=check('n');
        if(!presence)
        {
            buttons[13].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[13].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[13].setClickable(false);
        checkwin();
    }

    public void o(View view) {
        boolean presence=check('o');if(!presence)
        {
            buttons[14].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[14].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[14].setClickable(false);
        checkwin();
    }

    public void p(View view) {
        boolean presence=check('p');
        if(!presence)
        {
            buttons[15].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[15].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[15].setClickable(false);
        checkwin();
    }

    public void q(View view) {
        boolean presence=check('q');
        if(!presence)
        {
            buttons[16].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[16].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[16].setClickable(false);
        checkwin();
    }

    public void r(View view) {
        boolean presence=check('r');
        if(!presence)
        {
            buttons[17].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[17].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[17].setClickable(false);
        checkwin();
    }

    public void s(View view) {
        boolean presence=check('s');
        if(!presence)
        {
            buttons[18].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[18].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[18].setClickable(false);
        checkwin();
    }

    public void t(View view) {
        boolean presence=check('t');
        if(!presence)
        {
            buttons[19].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[19].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[19].setClickable(false);
        checkwin();
    }

    public void u(View view) {
        boolean presence=check('u');
        if(!presence)
        {
            buttons[20].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[20].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[20].setClickable(false);
        checkwin();
    }

    public void v(View view) {
        boolean presence= check('v');
        if(!presence)
        {
            buttons[21].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[21].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[21].setClickable(false);
        checkwin();
    }

    public void w(View view) {
        boolean presence=check('w');
        if(!presence)
        {
            buttons[22].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[22].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[22].setClickable(false);
        checkwin();
    }

    public void x(View view) {
        boolean presence=check('x');
        if(!presence)
        {
            buttons[23].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[23].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[23].setClickable(false);
        checkwin();
    }

    public void y(View view) {
        boolean presence=check('y');
        if(!presence)
        {
            buttons[24].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[24].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[24].setClickable(false);
        checkwin();
    }

    public void z(View view) {
        boolean presence=  check('z');
        if(!presence)
        {
            buttons[25].setBackgroundColor(Color.RED);
        }
        else
        {
            buttons[25].setBackgroundColor(Color.parseColor("#2E7D32"));
        }
        buttons[25].setClickable(false);
        checkwin();
    }

}
