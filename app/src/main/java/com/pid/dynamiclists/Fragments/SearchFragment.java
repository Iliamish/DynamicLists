package com.pid.dynamiclists.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pid.dynamiclists.Adapters.SearchAdapter;
import com.pid.dynamiclists.Interfaces.RecyclerViewClickListener;
import com.pid.dynamiclists.Models.SearchObject;
import com.pid.dynamiclists.Models.ShortStudent;
import com.pid.dynamiclists.Models.Student;

import java.util.ArrayList;
import java.util.List;

import com.pid.dynamiclists.Models.StudentInfoObject;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, RecyclerViewClickListener {

    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

    public RecyclerView recyclerView;
    public SearchAdapter adapter;
    public SearchView searchView;
    public TextView hint;

    List<ShortStudent> users;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter.removeLoader();
            adapter.add(null);
            SearchFragment.this.loadData(msg.obj.toString());
        }
    };

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        NetworkService.getInstance().getJSONApiUNN().getAbiturient(users.get(position).getNrecabit()).enqueue(new Callback<StudentInfoObject>() {
            @Override
            public void onResponse(Call<StudentInfoObject> call, Response<StudentInfoObject> response){
                System.out.println(response.body());
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, new StudentFragment(response.body(),users.get(position).getNrecabit()), "StudentFragment_Tag").addToBackStack("Search").commit();
            }

            @Override
            public void onFailure(Call<StudentInfoObject> call, Throwable t) {
                System.out.println(call.request().url());
                System.out.println("Error occurred while getting request!");
                t.printStackTrace();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // получаем элемент ListView
        recyclerView = view.findViewById(R.id.list);
        hint = view.findViewById(R.id.hint);
        searchView = view.findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getActivity().getResources().getString(R.string.search_hint));

        users = new ArrayList<>();
        // create Adapter
        adapter = new SearchAdapter(getActivity(), this, users);
        // Set Adapter
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //System.out.println(newText);
        if(newText.length() >= 3) {
            mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, newText), DEFAULT_AUTOCOMPLETE_DELAY);
        }else{
            adapter.clear();
            hint.setVisibility(View.VISIBLE);
        }
        return true;
    }

    public void loadData(String request){
        NetworkService.getInstance().getJSONApiUNN().getSearchList(request, 1).enqueue(new Callback<SearchObject>() {
            @Override
            public void onResponse(Call<SearchObject> call, Response<SearchObject> response){
                adapter.clear();
                if(searchView.getQuery().length() >= 3) {
                    List<ShortStudent> list = response.body().getAbits();
                    for (ShortStudent user : list) {
                        adapter.add(user);
                    }
                    adapter.notifyDataSetChanged();
                    hint.setVisibility(View.INVISIBLE);
                }else{
                    hint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchObject> call, Throwable t) {
                System.out.println(call.request().url());
                System.out.println("Error occurred while getting request!");
                t.printStackTrace();
            }
        });
    }
}
