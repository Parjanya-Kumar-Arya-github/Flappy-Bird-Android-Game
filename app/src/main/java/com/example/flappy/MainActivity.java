package com.example.flappy;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    protected long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView btn = (ImageView) findViewById(R.id.play);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),game.class);
            startActivity(intent);
            finish();

        });

    }
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}