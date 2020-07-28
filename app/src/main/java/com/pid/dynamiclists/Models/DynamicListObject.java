package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DynamicListObject {
    @SerializedName("dinspis")
    @Expose
    private List<Student> list;

    @SerializedName("places")
    @Expose
    private List<Place> places;

    @SerializedName("dis")
    @Expose
    private List<Subject> subjects;

    public List<Student> getList() {
        return list;
    }

    public void initLists(){
        this.list = new ArrayList<>();
        this.places = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
