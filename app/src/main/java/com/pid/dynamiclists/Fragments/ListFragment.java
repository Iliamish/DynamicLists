package com.pid.dynamiclists.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pid.dynamiclists.Activities.LevelActivity;
import com.pid.dynamiclists.Activities.MainActivity;
import com.pid.dynamiclists.Adapters.DynamicListAdapter;
import com.pid.dynamiclists.Adapters.MenuSpinnerAdapter;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.Models.DynamicListObject;
import com.pid.dynamiclists.Models.MainMenu;
import com.pid.dynamiclists.Models.MenuObject;
import com.pid.dynamiclists.Models.Place;
import com.pid.dynamiclists.Models.Student;
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
import androidx.fragment.app.FragmentManager;
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

    TextView lastMod;
    TextView placesCount;
    TextView peopleMass;

    public RecyclerView recyclerView;
    DynamicListAdapter listAdapter;

    DynamicListObject dynamicListObject;

    Button clearBtn;

    Context context;
    //List<Student> students;

    @Override
    public void onRefresh() {
        if(!NetworkService.getInstance().haveInternetConnection()) {
            System.out.println("\\\\ NO INTERNET CONNECTION \\\\");
            showToast("Нет соединения с интернетом", Toast.LENGTH_SHORT);
            pullToRefresh.setRefreshing(false);
        }else{
            getList();
            if(checkAllSpinnerEmpty()){
                getMenu(Configuration.emptyFac,Configuration.emptySpec, Configuration.emptyFin, Configuration.emptyForm);
            }
        }
    }

    public void showToast(String text, int duration){
        if(isAdded()) {
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        context = getActivity();

        faculties = new ArrayList<>();
        specialities = new ArrayList<>();
        form = new ArrayList<>();
        fin = new ArrayList<>();
        //List<Student> students = ;
        dynamicListObject = new DynamicListObject();
        dynamicListObject.initLists();

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context.getApplicationContext(), LevelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, getActivity().getSupportFragmentManager().findFragmentByTag("Search_tag")).addToBackStack(null).commit();
            }
        });


        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.dynamic_recyclerview);
        listAdapter = new DynamicListAdapter(getActivity(),dynamicListObject);
        recyclerView.setAdapter(listAdapter);

        collapsingToolbarLayout.setTitle("Динамические списки");
        myToolbar.setTitle("");
        myToolbar.setTitleMarginStart(-80);

        lastMod = view.findViewById(R.id.lastmod);
        placesCount = view.findViewById(R.id.places_counter);
        peopleMass = view.findViewById(R.id.size_div_places);

        clearBtn = view.findViewById(R.id.clear_menu_btn);
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
                lastMod.setVisibility(View.GONE);
                placesCount.setVisibility(View.GONE);
                peopleMass.setVisibility(View.GONE);
                getMenu(Configuration.emptyFac,Configuration.emptySpec, Configuration.emptyFin, Configuration.emptyForm);

                dynamicListObject.getList().clear();
                listAdapter.notifyDataSetChanged();
            }
        });

        if(!NetworkService.getInstance().haveInternetConnection()) {
            System.out.println("\\\\ NO INTERNET CONNECTION \\\\");
            showToast("Нет соединения с интернетом", Toast.LENGTH_LONG);
        }

        getMenu(Configuration.chousenFac,Configuration.chousenSpec, Configuration.chousenFin, Configuration.chousenForm);

        spinnerFac = collapsingToolbarLayout.findViewById(R.id.spinner);

        adapterFac = new MenuSpinnerAdapter(getActivity(),R.layout.simple_spinner_item, faculties);

        spinnerFac.setAdapter(adapterFac);

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

    private boolean checkAllSpinnerEmpty(){
        return spinnerSpec.getSelectedItem() == null &&
                spinnerFin.getSelectedItem() == null &&
                spinnerFac.getSelectedItem() == null &&
                spinnerForm.getSelectedItem() == null;
    }

    private boolean checkAtLeastOneSpinnerCanBeFilled(){
        return form.size() == 1 ||
                fin.size() == 1  ||
                specialities.size() == 1 ||
                faculties.size() == 1;
    }

    private boolean checkReadyForRequest(){
        return !Configuration.chousenSpec.equals(Configuration.emptySpec) &&
                !Configuration.chousenFac.equals(Configuration.emptyFac) &&
                !Configuration.chousenFin.equals(Configuration.emptyFin) &&
                !Configuration.chousenForm.equals(Configuration.emptyForm);
    }

    private boolean checkAllSpinnersClear(){
        return Configuration.chousenSpec == Configuration.emptySpec &&
                Configuration.chousenFac == Configuration.emptyFac &&
                Configuration.chousenFin == Configuration.emptyFin &&
                Configuration.chousenForm == Configuration.emptyForm;
    }

    private int getIndexFromList(List<MenuObject> list, String obj){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getNrec().equals(obj)){
                return i;
            }
        }
        return 0;
    }

    private void getMenu(String fac_str,  String spec_str, String fin_str, String form_str){
        NetworkService.getInstance()
                .getJSONApiUNN()
                .getMenu(1,Configuration.chousenLevel,fac_str,spec_str, fin_str, form_str, 1)
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

                        if(checkAtLeastOneSpinnerCanBeFilled()) {
                            if (checkReadyForRequest()) {
                                spinnerSpec.setSelection(getIndexFromList(specialities, Configuration.chousenSpec) + 1);
                                spinnerForm.setSelection(getIndexFromList(form, Configuration.chousenForm) + 1);
                                spinnerFac.setSelection(getIndexFromList(faculties, Configuration.chousenFac) + 1);
                                spinnerFin.setSelection(getIndexFromList(fin, Configuration.chousenFin) + 1);
                                clearBtn.setVisibility(View.VISIBLE);
                                getList();
                            } else {
                                boolean request = false;
                                if (Configuration.chousenForm.equals(Configuration.emptyForm)) {
                                    if (form.size() == 1) {
                                        Configuration.chousenForm = form.get(0).getNrec();
                                        spinnerForm.setSelection(1);
                                        request = true;
                                    }
                                }
                                if (Configuration.chousenFac.equals(Configuration.emptyFac)) {
                                    if (faculties.size() == 1) {
                                        Configuration.chousenFac = faculties.get(0).getNrec();
                                        spinnerFac.setSelection(1);
                                        request = true;
                                    }
                                }
                                if (Configuration.chousenFin.equals(Configuration.emptyFin)) {
                                    if (fin.size() == 1) {
                                        Configuration.chousenFin = fin.get(0).getNrec();
                                        spinnerFin.setSelection(1);
                                        request = true;
                                    }
                                }
                                if (Configuration.chousenSpec.equals(Configuration.emptySpec)) {
                                    if (specialities.size() == 1) {
                                        Configuration.chousenSpec = specialities.get(0).getNrec();
                                        spinnerSpec.setSelection(1);
                                        request = true;
                                    }
                                }

                                adapterFac.notifyDataSetChanged();
                                adapterSpec.notifyDataSetChanged();
                                adapterForm.notifyDataSetChanged();
                                adapterFin.notifyDataSetChanged();

                                if (request) {
                                    getMenu(Configuration.chousenFac, Configuration.chousenSpec, Configuration.chousenFin, Configuration.chousenForm);
                                }
                            }
                        }
//                        }else{
//                            if(checkReadyForRequest()){
//                                if(checkAllSpinnerEmpty()) {
//                                    spinnerSpec.setSelection(getIndexFromList(specialities, Configuration.chousenSpec) + 1);
//                                    spinnerForm.setSelection(getIndexFromList(form, Configuration.chousenForm) + 1);
//                                    spinnerFac.setSelection(getIndexFromList(faculties, Configuration.chousenFac) + 1);
//                                    spinnerFin.setSelection(getIndexFromList(fin, Configuration.chousenFin) + 1);
//                                    clearBtn.setVisibility(View.VISIBLE);
//                                    getList();
//                                }
//                            }
//                        }

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

                        if(resp.body() !=  null ) {
                            dynamicListObject.getList().clear();
                            dynamicListObject.getPlaces().clear();
                            dynamicListObject.getSubjects().clear();
                            dynamicListObject.getWaves().clear();
                            dynamicListObject.getList().addAll(resp.body().getList());
                            dynamicListObject.getPlaces().addAll(resp.body().getPlaces());
                            dynamicListObject.getSubjects().addAll(resp.body().getSubjects());
                            dynamicListObject.getWaves().addAll(resp.body().getWaves());


                            String fileInput = StorageIO.readFile(context.getFilesDir(), "favoriteList");
                            List<String> list;

                            int count = 0;

                            for(Place place : dynamicListObject.getPlaces()){
                                count += Integer.parseInt(place.getValue());
                            }

                            if(fileInput.equals("")) {
                                list = new ArrayList<>();
                            }
                            else{
                                Type listType = new TypeToken<ArrayList<String>>() {
                                }.getType();
                                list = new Gson().fromJson(fileInput, listType);
                            }

                            if(dynamicListObject.getList().size() != 0) {
                                List<Student> students = dynamicListObject.getList();
                                Student emst = new Student();
                                if(dynamicListObject.getPlaces().size() != 0) {
                                    emst.setCategnum(students.get(0).getCategnum());
                                }else{
                                    emst.setCategnum("-1");
                                }
                                students.add(0,emst);
                                for (int i = 1; i < students.size() - 1; i++) {
                                    //students.add(menu.getList().get(i));
                                    if (!students.get(i).getCategnum().equals(students.get(i + 1).getCategnum()) &&
                                            !(students.get(i).getCategnum().equals("50") && students.get(i + 1).getCategnum().equals("100"))) {
                                        Student emst2 = new Student();
                                        emst2.setCategnum(students.get(i + 1).getCategnum());
                                        students.add(i+1,emst2);
                                        i++;
                                    }
                                    if(list.indexOf(students.get(i).getNrecabit()) != -1){
                                        students.get(i).isFavourite = true;
                                    }
                                }


                                //students.addAll(menu.getList());
                                listAdapter.notifyDataSetChanged();

//                                if(Configuration.currentScrollPosition != 0){
//                                    recyclerView.smoothScrollToPosition(Configuration.currentScrollPosition);
//                                }

                                if(count != 0) {
                                    placesCount.setText("Бюджетных мест: " + count);
                                    placesCount.setVisibility(View.VISIBLE);

                                    double mass = (double) (resp.body().getList().size() * 10 / count) / 10;

                                    peopleMass.setText("Конкурс: " + mass + " человек/место");
                                    peopleMass.setVisibility(View.VISIBLE);
                                }
                                lastMod.setText("Последнее обновление: " + resp.body().getLastUpdate());
                                lastMod.setVisibility(View.VISIBLE);
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
