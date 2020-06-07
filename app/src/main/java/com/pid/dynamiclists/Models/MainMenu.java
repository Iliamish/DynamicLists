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

    public List<MenuObject> getFacs() {
        return facs;
    }

    public void setFacs(List<MenuObject> facs) {
        this.facs = facs;
    }

    public List<MenuObject> getSpecs() {
        return specs;
    }

    public void setSpecs(List<MenuObject> specs) {
        this.specs = specs;
    }

    public List<MenuObject> getForms() {
        return forms;
    }

    public void setForms(List<MenuObject> forms) {
        this.forms = forms;
    }

    public List<MenuObject> getFin() {
        return fin;
    }

    public void setFin(List<MenuObject> fin) {
        this.fin = fin;
    }
}
