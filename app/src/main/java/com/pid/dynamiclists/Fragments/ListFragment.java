package com.pid.dynamiclists.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pid.dynamiclists.Adapters.MenuSpinnerAdapter;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.Models.MainMenu;
import com.pid.dynamiclists.Models.MenuObject;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout pullToRefresh;


    MenuSpinnerAdapter adapterFac;
    MenuSpinnerAdapter adapterSpec;
    MenuSpinnerAdapter adapterForm;
    MenuSpinnerAdapter adapterFin;


    List<MenuObject> faculties;
    List<MenuObject> specialities;
    List<MenuObject> form;
    List<MenuObject> fin;

    MaterialSpinner spinnerFac;
    MaterialSpinner spinnerSpec;
    MaterialSpinner spinnerForm;
    MaterialSpinner spinnerFin;

    @Override
    public void onRefresh() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        faculties = new ArrayList<>();
        specialities = new ArrayList<>();
        form = new ArrayList<>();
        fin = new ArrayList<>();

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);

        collapsingToolbarLayout.setTitleEnabled(false);
        myToolbar.setTitle("");

        spinnerFac = collapsingToolbarLayout.findViewById(R.id.spinner);

        adapterFac = new MenuSpinnerAdapter(getActivity(),R.layout.simple_spinner_item,faculties);

        NetworkService.getInstance()
                .getJSONApiUNN()
                .getMenu(1,1,"fac.value","spec.value", "fin.value", "form.value", 1)
                .enqueue(new Callback<MainMenu>() {
                    @Override
                    public void onResponse(@NonNull Call<MainMenu> call, @NonNull Response<MainMenu> resp) {
                        faculties.addAll(resp.body().getFacs());
                        adapterFac.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(@NonNull Call<MainMenu> call, @NonNull Throwable t) {
                        System.out.println(call.request().url());
                        System.out.println("Error occurred while getting request!");
                    }
                });

        spinnerFac.setAdapter(adapterFac);

        spinnerFac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1) {
                    spinnerSpec.setVisibility(View.VISIBLE);
                    pullToRefresh.setRefreshing(true);
                    Configuration.chousenFac = faculties.get(position).getNrec();
                    NetworkService.getInstance()
                            .getJSONApiUNN()
                            .getMenu(1,1,Configuration.chousenFac,"spec.value", "fin.value", "form.value", 1)
                            .enqueue(new Callback<MainMenu>() {
                                @Override
                                public void onResponse(@NonNull Call<MainMenu> call, @NonNull Response<MainMenu> resp) {
                                    specialities.clear();
                                    specialities.addAll(resp.body().getSpecs());
                                    adapterSpec.notifyDataSetChanged();
                                    pullToRefresh.setRefreshing(false);
                                }

                                @Override
                                public void onFailure(@NonNull Call<MainMenu> call, @NonNull Throwable t) {
                                    if(pullToRefresh.isRefreshing()){
                                        pullToRefresh.setRefreshing(false);
                                    }
                                    System.out.println(call.request().url());
                                    System.out.println("Error occurred while getting request!");
                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSpec = collapsingToolbarLayout.findViewById(R.id.spinner2);
        adapterSpec = new MenuSpinnerAdapter(getActivity(),R.layout.simple_spinner_item, specialities);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        // Определяем разметку для использования при выборе элемента
        adapterSpec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerSpec.setAdapter(adapterSpec);

        spinnerSpec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1) {
                    spinnerFin.setVisibility(View.VISIBLE);
                    pullToRefresh.setRefreshing(true);
                    Configuration.chousenSpec = specialities.get(position).getNrec();
                    NetworkService.getInstance()
                            .getJSONApiUNN()
                            .getMenu(1,1,Configuration.chousenFac,Configuration.chousenSpec, "fin.value", "form.value", 1)
                            .enqueue(new Callback<MainMenu>() {
                                @Override
                                public void onResponse(@NonNull Call<MainMenu> call, @NonNull Response<MainMenu> resp) {
                                    fin.clear();
                                    fin.addAll(resp.body().getForms());
                                    adapterFin.notifyDataSetChanged();
                                    pullToRefresh.setRefreshing(false);
                                }

                                @Override
                                public void onFailure(@NonNull Call<MainMenu> call, @NonNull Throwable t) {
                                    if(pullToRefresh.isRefreshing()){
                                        pullToRefresh.setRefreshing(false);
                                    }
                                    System.out.println(call.request().url());
                                    System.out.println("Error occurred while getting request!");
                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerFin = collapsingToolbarLayout.findViewById(R.id.spinner3);
        adapterFin = new MenuSpinnerAdapter(getActivity(),R.layout.simple_spinner_item, fin);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        // Определяем разметку для использования при выборе элемента
        adapterFin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerFin.setAdapter(adapterFin);

        spinnerFin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1) {
                    spinnerForm.setVisibility(View.VISIBLE);
                    pullToRefresh.setRefreshing(true);
                    Configuration.chousenFin = fin.get(position).getNrec();
                    NetworkService.getInstance()
                            .getJSONApiUNN()
                            .getMenu(1,1,Configuration.chousenFac,Configuration.chousenSpec, Configuration.chousenFin, "form.value", 1)
                            .enqueue(new Callback<MainMenu>() {
                                @Override
                                public void onResponse(@NonNull Call<MainMenu> call, @NonNull Response<MainMenu> resp) {
                                    form.clear();
                                    form.addAll(resp.body().getFin());
                                    adapterForm.notifyDataSetChanged();
                                    pullToRefresh.setRefreshing(false);
                                }

                                @Override
                                public void onFailure(@NonNull Call<MainMenu> call, @NonNull Throwable t) {
                                    if(pullToRefresh.isRefreshing()){
                                        pullToRefresh.setRefreshing(false);
                                    }
                                    System.out.println(call.request().url());
                                    System.out.println("Error occurred while getting request!");
                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerForm = collapsingToolbarLayout.findViewById(R.id.spinner4);
        adapterForm = new MenuSpinnerAdapter(getActivity(),R.layout.simple_spinner_item, form);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        // Определяем разметку для использования при выборе элемента
        adapterForm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerForm.setAdapter(adapterForm);

        spinnerForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1) {
                    spinnerForm.setVisibility(View.VISIBLE);
                   // pullToRefresh.setRefreshing(true);
                    Configuration.chousenForm = form.get(position).getNrec();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


}
