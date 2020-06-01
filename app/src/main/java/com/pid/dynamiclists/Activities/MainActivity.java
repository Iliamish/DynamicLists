package com.pid.dynamiclists.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pid.dynamiclists.Models.MainMenu;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkService.getInstance()
                .getJSONApiUNN()
                .getMenu(1,1,"fac.value","spec.value", "fin.value", "form.value", 1)
                .enqueue(new Callback<MainMenu>() {
                    @Override
                    public void onResponse(@NonNull Call<MainMenu> call, @NonNull Response<MainMenu> resp) {
                        MainMenu menu = resp.body();

                    }

                    @Override
                    public void onFailure(@NonNull Call<MainMenu> call, @NonNull Throwable t) {
                        System.out.println(call.request().url());
                        System.out.println("Error occurred while getting request!");
                    }
                });
    }
}
