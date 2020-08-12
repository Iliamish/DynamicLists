package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Student {
    @SerializedName("nrecabit")
    @Expose
    private String nrecabit;

    @SerializedName("fio")
    @Expose
    private String fio;

    @SerializedName("mesto")
    @Expose
    private String mesto;

    @SerializedName("srmark")
    @Expose
    private String srmark;

    @SerializedName("timeorig")
    @Expose
    private String timeorig;

    @SerializedName("orignngu")
    @Expose
    private String orignngu;

    @SerializedName("orig")
    @Expose
    private String orig;

    @SerializedName("sogl")
    @Expose
    private String sogl;

    @SerializedName("nrecfin")
    @Expose
    private String nrecfin;

    @SerializedName("categ")
    @Expose
    private String categ;

    @SerializedName("categnum")
    @Expose
    private String categnum;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("summark")
    @Expose
    private String summark;

    @SerializedName("mark1")
    @Expose
    private String mark1;

    @SerializedName("mark2")
    @Expose
    private String mark2;

    @SerializedName("mark3")
    @Expose
    private String mark3;

    @SerializedName("mark4")
    @Expose
    private String mark4;

    @SerializedName("mark5")
    @Expose
    private String mark5;

    @SerializedName("mark6")
    @Expose
    private String mark6;

    @SerializedName("wave")
    @Expose
    private String wave;

    public boolean isFavourite = false;
    public boolean isExpanded = false;

    public String getNrecabit() {
        return nrecabit;
    }

    public void setNrecabit(String nrecabit) {
        this.nrecabit = nrecabit;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getSrmark() {
        return srmark;
    }

    public void setSrmark(String srmark) {
        this.srmark = srmark;
    }

    public String getTimeorig() {
        return timeorig;
    }

    public void setTimeorig(String timeorig) {
        this.timeorig = timeorig;
    }

    public String getOrignngu() {
        return orignngu;
    }

    public void setOrignngu(String orignngu) {
        this.orignngu = orignngu;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public String getSogl() {
        return sogl;
    }

    public void setSogl(String sogl) {
        this.sogl = sogl;
    }

    public String getNrecfin() {
        return nrecfin;
    }

    public void setNrecfin(String nrecfin) {
        this.nrecfin = nrecfin;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public String getCategnum() {
        return categnum;
    }

    public void setCategnum(String categnum) {
        this.categnum = categnum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummark() {
        return summark;
    }

    public void setSummark(String summark) {
        this.summark = summark;
    }

    public List<String> getMarksList(){
        List<String> marks = new ArrayList<>();
        marks.add(mark1);
        marks.add(mark2);
        marks.add(mark3);
        marks.add(mark4);
        marks.add(mark5);
        marks.add(mark6);

        return marks;
    }

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        this.mark3 = mark3;
    }

    public String getMark4() {
        return mark4;
    }

    public void setMark4(String mark4) {
        this.mark4 = mark4;
    }

    public String getMark5() {
        return mark5;
    }

    public void setMark5(String mark5) {
        this.mark5 = mark5;
    }

    public String getMark6() {
        return mark6;
    }

    public void setMark6(String mark6) {
        this.mark6 = mark6;
    }

    public String getWave() {
        return wave;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }
}
