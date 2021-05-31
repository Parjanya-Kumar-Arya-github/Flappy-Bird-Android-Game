package com.example.flappy;




import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class game_over extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Intent intent = getIntent();
        String score = intent.getStringExtra(GameView.MSG);
        TextView textView= (TextView) findViewById(R.id.score);
        textView.setText(score);
        ImageView play = (ImageView) findViewById(R.id.retry);
        play.setClickable(true);
        play.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent1);
            finish();
        });

    }
    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent1);
        finish();
    }
}