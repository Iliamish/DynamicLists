package com.pid.dynamiclists.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.Fragments.FavoriteFragment;
import com.pid.dynamiclists.Fragments.ListFragment;
import com.pid.dynamiclists.Fragments.SearchFragment;
import com.pid.dynamiclists.Models.DynamicListObject;
import com.pid.dynamiclists.Models.MainMenu;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;
import com.pid.dynamiclists.StorageIO.StorageIO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;


    final FavoriteFragment favouriteFragment = new FavoriteFragment();

    final ListFragment listFragment = new ListFragment();

    final SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_type);

        View view = findViewById(R.id.navigation_type); // BottomNavigationView menu item id
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Configuration.chousenSpec = Configuration.emptySpec;
                Configuration.chousenFac = Configuration.emptyFac;
                Configuration.chousenFin = Configuration.emptyFin;
                Configuration.chousenForm = Configuration.emptyForm;

//                List<String> list = new ArrayList<>();
////                StorageIO.writeFile(getFilesDir(), "favoriteList", new Gson().toJson(list));

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LevelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return false;
            }
        });

        changeLevel();

    }

    public void changeLevel(){
        switch (Configuration.chousenLevel){
            case 1:
                bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.ic_buchelor);
                bottomNavigationView.getMenu().getItem(0).setTitle("Бакалавриат");

                break;

            case 2:
                bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.ic_mag);
                bottomNavigationView.getMenu().getItem(0).setTitle("Магистратура");

                break;

            case 3:
                bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.ic_spo);
                bottomNavigationView.getMenu().getItem(0).setTitle("СПО");

                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navigation_favorite:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, favouriteFragment, "Favourite_tag").commit();
                return true;
            case R.id.navigation_type:
                // If user tab on menu schedule, while it visible, scroll view will move to top
                ListFragment myFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("List_tag");
                if (myFragment != null && myFragment.isVisible()) {
                    ((ListFragment)getSupportFragmentManager().getFragments().get(0)).recyclerView.smoothScrollToPosition(0);
                }else {
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, listFragment, "List_tag").commit();
                }
                return true;
            case R.id.navigation_search:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, searchFragment, "Search_tag").commit();
                return true;
        }

        return false;
    }

    public void goToChousenFragment(int id){
        switch(id){
            case R.id.navigation_favorite:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, favouriteFragment, "Favourite_tag").commit();

                bottomNavigationView.setSelectedItemId(R.id.navigation_favorite);

                break;
            case R.id.navigation_type:
                // If user tab on menu schedule, while it visible, scroll view will move to top

                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, listFragment, "List_tag").commit();

                bottomNavigationView.setSelectedItemId(R.id.navigation_type);

                break;
            case R.id.navigation_search:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, searchFragment, "Search_tag").commit();

                bottomNavigationView.setSelectedItemId(R.id.navigation_search);

                break;
        }

    }
}
