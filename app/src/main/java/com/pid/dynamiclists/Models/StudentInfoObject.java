package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentInfoObject {
    @SerializedName("dan")
    @Expose
    private List<String> dan;

    @SerializedName("marks")
    @Expose
    private List<StudentInfoSubject> marks;

    @SerializedName("apps")
    @Expose
    private List<StudentInfoSpecs> apps;

    public List<String> getDan() {
        return dan;
    }

    public void setDan(List<String> dan) {
        this.dan = dan;
    }

    public List<StudentInfoSubject> getMarks() {
        return marks;
    }

    public void setMarks(List<StudentInfoSubject> marks) {
        this.marks = marks;
    }

    public List<StudentInfoSpecs> getApps() {
        return apps;
    }

    public void setApps(List<StudentInfoSpecs> apps) {
        this.apps = apps;
    }
}
