package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchObject {
    @SerializedName("found")
    @Expose
    private int found;

    @SerializedName("abits")
    @Expose
    private List<ShortStudent> abits;

    public int getFound() {
        return found;
    }

    public void setFound(int found) {
        this.found = found;
    }

    public List<ShortStudent> getAbits() {
        return abits;
    }

    public void setAbits(List<ShortStudent> abits) {
        this.abits = abits;
    }
}
