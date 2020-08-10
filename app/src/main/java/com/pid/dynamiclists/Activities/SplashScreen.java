package com.pid.dynamiclists.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.R;
import com.pid.dynamiclists.StorageIO.StorageIO;

import java.lang.reflect.Type;

/**
 * The type Splash screen.
 * @author Ilya Mishin
 */
public class SplashScreen extends Activity
{

    /**
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(this::startApp,1500);
    }

    /**
     * Check saved authorization
     * If there is no saved data start login screen
     */
    private void startApp() {
        Intent intent = new Intent();
        String fileInput = StorageIO.readFile(getFilesDir(), "currentLevel");
        if(fileInput.equals("")) {
            intent.setClass(getApplicationContext(), LevelActivity.class);
        }
        else{
            Configuration.chousenLevel = Integer.parseInt(fileInput);
            intent.setClass(getApplicationContext(), MainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
