package com.pid.dynamiclists.Network;

import com.pid.dynamiclists.Models.MainMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {

    @GET("menu.php")
    public Call<MainMenu> getFaculties(@Query(value = "fac") int fac, @Query(value = "spec") int spec,
                                       @Query(value = "fin") int fin,@Query(value = "form") int form,
                                       @Query(value = "json") boolean json);

}
