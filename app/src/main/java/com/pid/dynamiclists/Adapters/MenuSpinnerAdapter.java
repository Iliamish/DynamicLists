package com.pid.dynamiclists.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import com.pid.dynamiclists.Models.MenuObject;
import com.pid.dynamiclists.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuSpinnerAdapter extends ArrayAdapter<MenuObject> {

    List<MenuObject> objects;

    public MenuSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<MenuObject> obj) {
        super(context, resource, obj);
        objects = obj;
    }


    @Override
    public int getCount() {
        if(objects != null)
            return objects.size();
        else
            return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_spinner_item2, parent, false);
        TextView text = view.findViewById(R.id.text_spinner);
        text.setText(objects.get(position).getValue());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //super.getDropDownView(position, convertView, parent);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_spinner_item, parent, false);
        TextView text = view.findViewById(R.id.text_spinner);
        text.setText(objects.get(position).getValue());
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
