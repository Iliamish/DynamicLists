package com.pid.dynamiclists.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.InetAddress;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Network service.
 * @author Ilya Mishin
 */
public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL_UNN = "https://enter.unn.ru/preport/stat/";
    private Retrofit mRetrofitUNN;

    private NetworkService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofitUNN = new Retrofit.Builder()
                .baseUrl(BASE_URL_UNN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Have internet connection boolean.
     *
     * @return true if device have internet connection
     */
    public boolean haveInternetConnection(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    /**
     * Gets json api unn.
     *
     * @return the json api unn
     */
    public NetworkInterface getJSONApiUNN() {
        return mRetrofitUNN.create(NetworkInterface.class);
    }
}