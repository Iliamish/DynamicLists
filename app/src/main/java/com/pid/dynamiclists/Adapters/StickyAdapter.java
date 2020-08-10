package com.pid.dynamiclists.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pid.dynamiclists.Models.DynamicListObject;
import com.pid.dynamiclists.Models.MenuObject;
import com.pid.dynamiclists.Models.Student;
import com.pid.dynamiclists.R;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

public class StickyAdapter extends SectioningAdapter {
    private class Section {
        ArrayList<Student> people = new ArrayList<>();
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView personNameTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            personNameTextView = itemView.findViewById(R.id.fio);
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {


        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    private List<Student> people;
    private ArrayList<Section> sections = new ArrayList<>();

    public StickyAdapter(List<Student> students) {
        people = students;
        Section currentSection = new Section();

        for (Student person : people) {
            currentSection.people.add(person);
        }
        sections.add(currentSection);
    }

    public void updatePeople() {
        sections.clear();

        // sort people into buckets by the first letter of last name

    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return people.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_table_info, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section s = sections.get(sectionIndex);
        ItemViewHolder ivh = (ItemViewHolder) viewHolder;
        Student person = s.people.get(itemIndex);
        ivh.personNameTextView.setText(person.getFio());
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
    }

}
