package com.pid.dynamiclists.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentInfoSpecs {
    @SerializedName("level")
    @Expose
    private int level;

    @SerializedName("formname")
    @Expose
    private String formname;

    @SerializedName("spec")
    @Expose
    private String spec;

    @SerializedName("fac")
    @Expose
    private String fac;

    @SerializedName("finname")
    @Expose
    private String finname;

    @SerializedName("orig")
    @Expose
    private String orig;


    @SerializedName("mesto")
    @Expose
    private String mesto;

    @SerializedName("iforig")
    @Expose
    private String iforig;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("summark")
    @Expose
    private String summark;

    @SerializedName("kol")
    @Expose
    private String kol;

    @SerializedName("nrecspec")
    @Expose
    private String nrecspec;

    @SerializedName("nrecfac")
    @Expose
    private String nrecfac;

    @SerializedName("forma")
    @Expose
    private String nrecform;

    @SerializedName("nrecfin")
    @Expose
    private String nrecfin;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getFac() {
        return fac;
    }

    public void setFac(String fac) {
        this.fac = fac;
    }

    public String getFinname() {
        return finname;
    }

    public void setFinname(String finname) {
        this.finname = finname;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getIforig() {
        return iforig;
    }

    public void setIforig(String iforig) {
        this.iforig = iforig;
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

    public String getKol() {
        return kol;
    }

    public void setKol(String kol) {
        this.kol = kol;
    }

    public String getNrecspec() {
        return nrecspec;
    }

    public void setNrecspec(String nrecspec) {
        this.nrecspec = nrecspec;
    }

    public String getNrecfac() {
        return nrecfac;
    }

    public void setNrecfac(String nrecfac) {
        this.nrecfac = nrecfac;
    }

    public String getNrecform() {
        return nrecform;
    }

    public void setNrecform(String nrecform) {
        this.nrecform = nrecform;
    }

    public String getNrecfin() {
        return nrecfin;
    }

    public void setNrecfin(String nrecfin) {
        this.nrecfin = nrecfin;
    }
}
