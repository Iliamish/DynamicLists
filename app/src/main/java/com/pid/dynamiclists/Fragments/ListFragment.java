package com.pid.dynamiclists.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pid.dynamiclists.Adapters.DynamicListAdapter;
import com.pid.dynamiclists.Adapters.MenuSpinnerAdapter;
import com.pid.dynamiclists.Adapters.StickyAdapter;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.Models.DynamicListObject;
import com.pid.dynamiclists.Models.MainMenu;
import com.pid.dynamiclists.Models.MenuObject;
import com.pid.dynamiclists.Models.Student;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
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

    RecyclerView recyclerView;
    DynamicListAdapter listAdapter;

    List<Student> students;

    @Override
    public void onRefresh() {
        getList();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        faculties = new ArrayList<>();
        specialities = new ArrayList<>();
        form = new ArrayList<>();
        fin = new ArrayList<>();
        students = new ArrayList<>();

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.dynamic_recyclerview);
        listAdapter = new DynamicListAdapter(students);
        recyclerView.setAdapter(listAdapter);

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle("Динамические списки");
        myToolbar.setTitle("");

        final Button clearBtn = view.findViewById(R.id.clear_menu_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration.chousenSpec = Configuration.emptySpec;
                Configuration.chousenFac = Configuration.emptyFac;
                Configuration.chousenFin = Configuration.emptyFin;
                Configuration.chousenForm = Configuration.emptyForm;

                spinnerFac.setSelection(0);
                spinnerSpec.setSelection(0);
                spinnerForm.setSelection(0);
                spinnerFin.setSelection(0);

                clearBtn.setVisibility(View.GONE);
                getMenu(Configuration.emptyFac,Configuration.emptySpec, Configuration.emptyFin, Configuration.emptyForm);

                students.clear();
                listAdapter.notifyDataSetChanged();
            }
        });

        spinnerFac = collapsingToolbarLayout.findViewById(R.id.spinner);

        adapterFac = new MenuSpinnerAdapter(getActivity(),R.layout.simple_spinner_item,faculties);

        spinnerFac.setAdapter(adapterFac);

        getMenu(Configuration.emptyFac,Configuration.emptySpec, Configuration.emptyFin, Configuration.emptyForm);

        spinnerFac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1) {
                    pullToRefresh.setRefreshing(true);

                    Configuration.chousenFac = faculties.get(position).getNrec();
                    if(checkReadyForRequest()){
                        getList();
                    }else{
                        getMenu(Configuration.chousenFac,Configuration.chousenSpec, Configuration.chousenFin, Configuration.chousenForm);
                        clearBtn.setVisibility(View.VISIBLE);
                    }
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
                    pullToRefresh.setRefreshing(true);
                    Configuration.chousenSpec = specialities.get(position).getNrec();
                    if(checkReadyForRequest()){
                        getList();
                    }else{
                        getMenu(Configuration.chousenFac,Configuration.chousenSpec, Configuration.chousenFin, Configuration.chousenForm);
                        clearBtn.setVisibility(View.VISIBLE);
                    }
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
                    if(checkReadyForRequest()){
                        getList();
                    }else{
                        getMenu(Configuration.chousenFac,Configuration.chousenSpec, Configuration.chousenFin, Configuration.chousenForm);
                        clearBtn.setVisibility(View.VISIBLE);
                    }
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
                   // pullToRefresh.setRefreshing(true);
                    Configuration.chousenForm = form.get(position).getNrec();
                    pullToRefresh.setRefreshing(true);

                    if(checkReadyForRequest()){
                        getList();
                    }else{
                        getMenu(Configuration.chousenFac,Configuration.chousenSpec, Configuration.chousenFin, Configuration.chousenForm);
                        clearBtn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private boolean checkReadyForRequest(){
        return Configuration.chousenSpec != Configuration.emptySpec &&
                Configuration.chousenFac != Configuration.emptyFac &&
                Configuration.chousenFin != Configuration.emptyFin &&
                Configuration.chousenForm != Configuration.emptyForm;
    }

    private boolean checkAllSpinnersClear(){
        return Configuration.chousenSpec == Configuration.emptySpec &&
                Configuration.chousenFac == Configuration.emptyFac &&
                Configuration.chousenFin == Configuration.emptyFin &&
                Configuration.chousenForm == Configuration.emptyForm;
    }

    private void getMenu(String fac_str,  String spec_str, String fin_str, String form_str){
        NetworkService.getInstance()
                .getJSONApiUNN()
                .getMenu(1,1,fac_str,spec_str, fin_str, form_str, 1)
                .enqueue(new Callback<MainMenu>() {
                    @Override
                    public void onResponse(@NonNull Call<MainMenu> call, @NonNull Response<MainMenu> resp) {
                        form.clear();
                        form.addAll(resp.body().getForms());
                        fin.clear();
                        fin.addAll(resp.body().getFin());
                        specialities.clear();
                        specialities.addAll(resp.body().getSpecs());
                        faculties.clear();
                        faculties.addAll(resp.body().getFacs());

                        adapterFac.notifyDataSetChanged();
                        adapterSpec.notifyDataSetChanged();
                        adapterForm.notifyDataSetChanged();
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

    private void getList(){
        NetworkService.getInstance()
                .getJSONApiUNN()
                .getDynamicListByPath(Configuration.chousenFac + (Configuration.chousenSpec.length() == 15 ?
                (Configuration.chousenSpec + "0") : Configuration.chousenSpec) + Configuration.chousenFin+ Configuration.chousenForm)
                .enqueue(new Callback<DynamicListObject>() {
                    @Override
                    public void onResponse(@NonNull Call<DynamicListObject> call, @NonNull Response<DynamicListObject> resp) {
                        if(pullToRefresh.isRefreshing()){
                            pullToRefresh.setRefreshing(false);
                        }
                        DynamicListObject menu = resp.body();
                        if(menu !=  null ) {
                            if(menu.getList().size() != 0) {
                                students.clear();
                                Student emst = new Student();
                                emst.setCategnum(menu.getList().get(0).getCategnum());
                                students.add(emst);
                                for (int i = 0; i < menu.getList().size() - 1; i++) {
                                    students.add(menu.getList().get(i));
                                    if (!menu.getList().get(i).getCategnum().equals(menu.getList().get(i + 1).getCategnum())) {
                                        Student emst2 = new Student();
                                        emst2.setCategnum(menu.getList().get(i + 1).getCategnum());
                                        students.add(emst2);
                                        i++;
                                    }
                                }
                                students.add(menu.getList().get(menu.getList().size() - 1));
                                //students.addAll(menu.getList());
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DynamicListObject> call, @NonNull Throwable t) {
                        if(pullToRefresh.isRefreshing()){
                            pullToRefresh.setRefreshing(false);
                        }
                        System.out.println(call.request().url());
                        System.out.println("Error occurred while getting request!");
                    }
                });
    }

}
