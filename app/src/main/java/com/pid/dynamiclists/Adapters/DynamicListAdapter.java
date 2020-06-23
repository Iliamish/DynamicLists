package com.pid.dynamiclists.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pid.dynamiclists.Models.Student;
import com.pid.dynamiclists.R;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DynamicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int CATEGORY_TYPE = 0;
    final int STUDENT_TYPE = 1;

    List<Student> students;

    public DynamicListAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == STUDENT_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new DynamicListAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_priority_line, parent, false);
            return new DynamicListAdapter.CategoryHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder view, int position) {
        if (view instanceof DynamicListAdapter.ViewHolder) {
            DynamicListAdapter.ViewHolder viewHolder  = (DynamicListAdapter.ViewHolder)view;
            Student student = students.get(position);

            viewHolder.nameView.setText(student.getFio());
            viewHolder.numView.setText(student.getMesto());
            viewHolder.pointsView.setText(student.getSummark());
        }else if(view instanceof DynamicListAdapter.CategoryHolder){
            DynamicListAdapter.CategoryHolder viewHolder  = (DynamicListAdapter.CategoryHolder)view;
            Student student = students.get(position);

            String[] params = view.itemView.getContext().getResources().getStringArray(R.array.categories);

            int pos = Integer.parseInt(student.getCategnum());
            if (pos == 100){
                pos = 6;
            }
            viewHolder.nameView.setText(params[pos]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(students.get(position).getFio() != null)
            return STUDENT_TYPE;
        else
            return CATEGORY_TYPE;
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView, numView, pointsView;
        final LinearLayout bottomLayout;


        ViewHolder(View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bottomLayout.getVisibility() == View.GONE) {
                        bottomLayout.setVisibility(View.VISIBLE);
                    }else{
                        bottomLayout.setVisibility(View.GONE);
                    }
                }
            });
            bottomLayout = view.findViewById(R.id.linear_bottom);
            nameView = view.findViewById(R.id.fio);
            numView = view.findViewById(R.id.position);
            pointsView = view.findViewById(R.id.points);
            /*infoView = view.findViewById(R.id.text2);
            image = view.findViewById(R.id.type_icon);*/
        }
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{
        final TextView nameView;


        CategoryHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.categ);
        }
    }
}
