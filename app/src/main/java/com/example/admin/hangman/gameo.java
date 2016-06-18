package com.example.admin.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class gameo extends AppCompatActivity {
    Random rn;
    String game_word;
    int lives = 5;
    int score=0;

    InputStream ips = null;
    ArrayList<String> wordlist;
    ArrayList<String> finished = new ArrayList<>();
    TextView gameView,liveView,scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameo);
        gameView = (TextView)findViewById(R.id.textView2);
        liveView = (TextView)findViewById(R.id.textView5);
        scoreView = (TextView)findViewById(R.id.textView6);
        gameView.setText("");
        try {
            ips = getAssets().open("animal.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
         wordlist = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(ips));
        String line;
        //char[] a = {'a'};

        try {
            while ((line = in.readLine()) != null) {
                String word = line.trim();
                wordlist.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("on",String.valueOf(wordlist.size()));

    }

    public void generate_new_word(View view) {
        rn = new Random();
        int l = wordlist.size();
        do {
            int index = rn.nextInt(l);
            game_word = wordlist.get(index);
        }while (finished.contains(game_word));
        finished.add(game_word);
        Log.d("on",game_word);
        int word_length = game_word.length();
        int i=word_length;
        gameView.setText("");
        while(i>0){
            gameView.append("*");
            i--;
        }


    }
    public void check(char a){
        int count=0;
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=0;i<game_word.length();i++){
            if(game_word.charAt(i)==a){
                    arr.add(i);
                    //gameView.setText(,i,1);
                    count++;
                }
            }
        if(count==0){
            lives--;
            liveView.setText(String.valueOf(lives));
            if(lives==0){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }

        }
        else{
            CharSequence set=null;
            set = gameView.getText();
            Log.d("on",String.valueOf(set.length())+" "+gameView.getText());
            gameView.setText("");
            for(int i =0;i<game_word.length();i++){
                if(!arr.contains(i)){
                    gameView.append(String.valueOf(set.charAt(i)));
                }
                else{
                    gameView.append(String.valueOf(a));
                }
            }
        }
        if(count==game_word.length()){
            score++;
            scoreView.setText(String.valueOf(score));


        }

    }

    public void guess(View view) {

    }

    public void a(View view) {
        check('a');
    }

    public void b(View view) {
        check('b');
    }

    public void c(View view) {
        check('c');
    }

    public void d(View view) {
        check('d');
    }

    public void e(View view) {
        check('e');
    }

    public void f(View view) {
        check('f');
    }

    public void g(View view) {
        check('g');
    }

    public void h(View view) {
        check('h');
    }

    public void i(View view) {
        check('i');
    }

    public void j(View view) {
        check('j');
    }

    public void k(View view) {
        check('k');
    }

    public void l(View view) {
        check('l');
    }

    public void m(View view) {
        check('m');
    }

    public void n(View view) {
        check('n');
    }

    public void o(View view) {
        check('o');
    }

    public void p(View view) {
        check('p');
    }

    public void q(View view) {
        check('q');
    }

    public void r(View view) {
        check('r');
    }

    public void s(View view) {
        check('s');
    }

    public void t(View view) {
        check('t');
    }

    public void u(View view) {
        check('u');
    }

    public void v(View view) {
        check('v');
    }

    public void w(View view) {
        check('w');
    }

    public void x(View view) {
        check('x');
    }

    public void y(View view) {
        check('y');
    }

    public void z(View view) {
        check('z');
    }
}