package com.pid.dynamiclists.Fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pid.dynamiclists.Adapters.DynamicListAdapter;
import com.pid.dynamiclists.Adapters.StudentInfoAdapter;
import com.pid.dynamiclists.Models.StudentInfoObject;
import com.pid.dynamiclists.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class StudentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    StudentInfoObject infoObject;

    SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    StudentInfoAdapter listAdapter;

    List<TextView> marksViews;
    List<TextView> subjViews;

    @Override
    public void onRefresh() {
        pullToRefresh.setRefreshing(false);
    }

    public StudentFragment(StudentInfoObject infoObject) {
        super();
        this.infoObject = infoObject;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(this);

        listAdapter = new StudentInfoAdapter(getContext(), infoObject.getApps());

        recyclerView = view.findViewById(R.id.student_recyclerview);
        recyclerView.setAdapter(listAdapter);

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(infoObject.getDan().get(0));
        myToolbar.setTitle("");

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

        return view;
    }
}
