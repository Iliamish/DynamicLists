package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainMenu {
    @SerializedName("fac")
    @Expose
    private List<MenuObject> facs;

    @SerializedName("spec")
    @Expose
    private List<MenuObject> specs;

    @SerializedName("form")
    @Expose
    private List<MenuObject> forms;

    @SerializedName("fin")
    @Expose
    private List<MenuObject> fin;
}
