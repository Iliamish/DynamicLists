package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShortStudent {
    @SerializedName("nrecabit")
    @Expose
    private String nrecabit;

    @SerializedName("fio")
    @Expose
    private String fio;

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
}
