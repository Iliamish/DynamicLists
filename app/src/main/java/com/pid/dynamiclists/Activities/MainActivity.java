package com.pid.dynamiclists.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.pid.dynamiclists.Fragments.FavoriteFragment;
import com.pid.dynamiclists.Fragments.ListFragment;
import com.pid.dynamiclists.Fragments.SearchFragment;
import com.pid.dynamiclists.Models.DynamicListObject;
import com.pid.dynamiclists.Models.MainMenu;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;

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

        NetworkService.getInstance()
                .getJSONApiUNN()
                .getDynamicListByPath("28147497671098028147497671072102814749767198850")
                .enqueue(new Callback<DynamicListObject>() {
                    @Override
                    public void onResponse(@NonNull Call<DynamicListObject> call, @NonNull Response<DynamicListObject> resp) {
                        DynamicListObject menu = resp.body();

                    }

                    @Override
                    public void onFailure(@NonNull Call<DynamicListObject> call, @NonNull Throwable t) {
                        System.out.println(call.request().url());
                        System.out.println("Error occurred while getting request!");
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navigation_favorite:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, favouriteFragment, "Favourite_tag").commit();
                return true;
            case R.id.navigation_type:
                // If user tab on menu schedule, while it visible, scroll view will move to top
                ListFragment myFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("Schedule_tag");
                if (myFragment != null && myFragment.isVisible()) {
                    //((Schedule)getSupportFragmentManager().getFragments().get(0)).scheduleDaysView.smoothScrollToPosition(0);
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
}
