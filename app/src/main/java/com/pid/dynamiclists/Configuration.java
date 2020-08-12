package com.pid.dynamiclists;

public class Configuration {

    public static String chousenFac = "fac.value";

    public static String chousenSpec = "spec.value";

    public static String chousenForm = "form.value";

    public static String chousenFin = "fin.value";

    public static int chousenLevel = 1;

    public static final String emptyFac = "fac.value";

    public static final String emptySpec = "spec.value";

    public static final String emptyForm = "form.value";

    public static final String emptyFin = "fin.value";

    public static int currentScrollPosition = 0;

    public static int getStatusColor(String status){
        switch (status){
            case "зачислен":
                return 0;
            case "к зачислению":
                return 1;
            case "заявление принято":
                return 2;
            default:
                return 3;
        }
    };

    public static String statusColorArray[] = {"#00ff00", "#df9d1c", "#0057d9", "#ff0000"};

    public static String waveColorArray[] = {"#ffffff", "#fcffd1", "#e5f1f5"};
}
