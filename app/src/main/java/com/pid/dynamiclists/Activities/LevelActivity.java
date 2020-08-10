package com.pid.dynamiclists.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.R;
import com.pid.dynamiclists.StorageIO.StorageIO;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        ImageView buch = findViewById(R.id.buchelor);
        ImageView mag = findViewById(R.id.magistrature);
        ImageView spo = findViewById(R.id.spo);

        buch.setOnClickListener(v -> {
            Intent intent = new Intent();

            Configuration.chousenLevel = 1;
            StorageIO.writeFile(getFilesDir(), "currentLevel", new Gson().toJson(1));

            intent.setClass(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        mag.setOnClickListener(v -> {
            Intent intent = new Intent();

            Configuration.chousenLevel = 2;
            StorageIO.writeFile(getFilesDir(), "currentLevel", new Gson().toJson(2));

            intent.setClass(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        spo.setOnClickListener(v -> {
            Intent intent = new Intent();

            Configuration.chousenLevel = 3;
            StorageIO.writeFile(getFilesDir(), "currentLevel", new Gson().toJson(3));

            intent.setClass(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
