package com.pid.dynamiclists.Fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pid.dynamiclists.Adapters.DynamicListAdapter;
import com.pid.dynamiclists.Adapters.StudentInfoAdapter;
import com.pid.dynamiclists.Models.StudentInfoObject;
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

public class StudentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    StudentInfoObject infoObject;
    String nrecabit;

    SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    StudentInfoAdapter listAdapter;
    ToggleButton favourite;

    List<TextView> marksViews;
    List<TextView> subjViews;

    @Override
    public void onRefresh() {
        pullToRefresh.setRefreshing(false);
    }

    public StudentFragment(StudentInfoObject infoObject, String nrecabit) {
        super();
        this.infoObject = infoObject;
        this.nrecabit = nrecabit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
                //getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, getActivity().getSupportFragmentManager().findFragmentByTag("Search_tag")).addToBackStack(null).commit();
            }
        });

        collapsingToolbarLayout.setTitle(infoObject.getDan().get(0));
        myToolbar.setTitle("");
        myToolbar.setTitleMarginStart(-80);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(this);

        favourite = view.findViewById(R.id.fav_button);

        listAdapter = new StudentInfoAdapter(getActivity(), infoObject.getApps());

        recyclerView = view.findViewById(R.id.student_recyclerview);
        recyclerView.setAdapter(listAdapter);

        //myToolbar.setTitle(infoObject.getDan().get(0));

        TextView status = view.findViewById(R.id.abit_stat);
        TextView soglNNGU = view.findViewById(R.id.sogl_nngu);
        TextView level = view.findViewById(R.id.level);

        status.setText(Html.fromHtml("<b>Статус абитуриента: </b><font color=\"#0057D9\">" + infoObject.getDan().get(1) + "</font>", Html.FROM_HTML_MODE_COMPACT));
        soglNNGU.setText(Html.fromHtml("<b>Согласие в ННГУ: </b>" + (infoObject.getDan().get(2).equals("Да") ? "<font color=\"#00ff00\">" : "<font color=\"#ff0000\">") +  infoObject.getDan().get(2) + "</font>", Html.FROM_HTML_MODE_COMPACT));
        level.setText(Html.fromHtml("<b>Уровень подготовки: </b>" + infoObject.getDan().get(3), Html.FROM_HTML_MODE_COMPACT));

        marksViews = new ArrayList<>();

        marksViews.add((TextView) view.findViewById(R.id.mark1));
        marksViews.add((TextView) view.findViewById(R.id.mark2));
        marksViews.add((TextView) view.findViewById(R.id.mark3));
        marksViews.add((TextView) view.findViewById(R.id.mark4));
        marksViews.add((TextView) view.findViewById(R.id.mark5));
        marksViews.add((TextView) view.findViewById(R.id.mark6));


        subjViews = new ArrayList<>();

        subjViews.add((TextView) view.findViewById(R.id.subj1));
        subjViews.add((TextView) view.findViewById(R.id.subj2));
        subjViews.add((TextView) view.findViewById(R.id.subj3));
        subjViews.add((TextView) view.findViewById(R.id.subj4));
        subjViews.add((TextView) view.findViewById(R.id.subj5));
        subjViews.add((TextView) view.findViewById(R.id.subj6));


        for (int i = 0; i < infoObject.getMarks().size() ; i++){
            marksViews.get(i).setVisibility(View.VISIBLE);
            marksViews.get(i).setText(infoObject.getMarks().get(i).getMark().equals("В обработке") ? "\u231b" : infoObject.getMarks().get(i).getMark());
            subjViews.get(i).setVisibility(View.VISIBLE);
            subjViews.get(i).setText(infoObject.getMarks().get(i).getName());
        }

//        marksViews.get(infoObject.getMarks().size() - 1).setVisibility(View.VISIBLE);
//        marksViews.get(infoObject.getMarks().size() - 1).setText(infoObject.getMarks().get(infoObject.getMarks().size() - 1).getMark().equals("В обработке") ? "\u231b" : infoObject.getMarks().get(infoObject.getMarks().size() - 1).getMark());
//        subjViews.get(infoObject.getMarks().size() - 1).setVisibility(View.VISIBLE);
//        subjViews.get(infoObject.getMarks().size() - 1).setText("Идивидуальные достижения");

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

        if(list.indexOf(nrecabit) != -1){
            favourite.setChecked(true);
        }else{
            favourite.setChecked(false);
        }

        favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String fileInput = StorageIO.readFile(getActivity().getFilesDir(), "favoriteList");

                List<String> list;

                if(isChecked){
                    if(fileInput.equals("")) {
                        list = new ArrayList<>();
                    }
                    else{
                        Type listType = new TypeToken<ArrayList<String>>() {
                        }.getType();
                        list = new Gson().fromJson(fileInput, listType);
                    }
                    if(list.indexOf(nrecabit) == -1) {
                        list.add(nrecabit);
                    }
                }else{
                    Type listType = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    list = new Gson().fromJson(fileInput, listType);

                    list.remove(nrecabit);
                }
                StorageIO.writeFile(getActivity().getFilesDir(), "favoriteList", new Gson().toJson(list));
            }
        });

        return view;
    }
}
