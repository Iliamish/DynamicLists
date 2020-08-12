package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wave {
    @SerializedName("firstwave")
    @Expose
    private String firstwave;

    @SerializedName("secondwave")
    @Expose
    private String secondwave;

    @SerializedName("val")
    @Expose
    private String val;

    public String getFirstwave() {
        return firstwave;
    }

    public void setFirstwave(String firstwave) {
        this.firstwave = firstwave;
    }

    public String getSecondwave() {
        return secondwave;
    }

    public void setSecondwave(String secondwave) {
        this.secondwave = secondwave;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
