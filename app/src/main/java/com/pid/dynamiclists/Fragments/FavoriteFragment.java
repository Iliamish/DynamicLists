package com.pid.dynamiclists.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pid.dynamiclists.Adapters.DynamicListAdapter;
import com.pid.dynamiclists.Adapters.FavoriteAdapter;
import com.pid.dynamiclists.Adapters.StudentInfoAdapter;
import com.pid.dynamiclists.Models.SearchObject;
import com.pid.dynamiclists.Models.ShortStudent;
import com.pid.dynamiclists.Models.StudentInfoObject;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;
import com.pid.dynamiclists.StorageIO.StorageIO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout pullToRefresh;

    RecyclerView recyclerView;
    FavoriteAdapter listAdapter;
    List<StudentInfoObject> favorites;

    @Override
    public void onRefresh() {
        pullToRefresh.setRefreshing(false);
    }

    public void getFavouriteStudent(String nrec){
        NetworkService.getInstance().getJSONApiUNN().getAbiturient(nrec).enqueue(new Callback<StudentInfoObject>() {
            @Override
            public void onResponse(Call<StudentInfoObject> call, Response<StudentInfoObject> response){
                System.out.println(response.body());
                favorites.add(response.body());
                listAdapter.notifyItemInserted(favorites.size() - 1);
                pullToRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<StudentInfoObject> call, Throwable t) {
                System.out.println(call.request().url());
                System.out.println("Error occurred while getting request!");
                pullToRefresh.setRefreshing(false);
                t.printStackTrace();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_fav);
        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(this);

        favorites = new ArrayList<>();
        listAdapter = new FavoriteAdapter(getContext(), favorites);

        recyclerView = view.findViewById(R.id.favorite_recyclerview);
        recyclerView.setAdapter(listAdapter);

        String fileInput = StorageIO.readFile(getActivity().getFilesDir(), "favoriteList");
        List<String> list;

        if(fileInput.equals("")) {
            list = new ArrayList<>();
        }
        else{
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            list = new Gson().fromJson(fileInput, listType);
        }

        if(list.size() != 0) {
            pullToRefresh.setRefreshing(true);
        }
        for (int i = 0; i < list.size(); i++ ){
            getFavouriteStudent(list.get(i));
        }

        collapsingToolbarLayout.setTitleEnabled(false);
        //collapsingToolbarLayout.setTitle("Мое избранное");
        myToolbar.setTitle("Мое избранное");
        return view;
    }
}
