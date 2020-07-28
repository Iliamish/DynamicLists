package com.pid.dynamiclists.Network;

import com.pid.dynamiclists.Models.DynamicListObject;
import com.pid.dynamiclists.Models.MainMenu;
import com.pid.dynamiclists.Models.SearchObject;
import com.pid.dynamiclists.Models.StudentInfoObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkInterface {

    @GET("menu.php")
    Call<MainMenu> getMenu(@Query(value = "list") int list, @Query(value = "level") int level,
                                  @Query(value = "fac") String fac, @Query(value = "spec") String spec,
                                       @Query(value = "fin") String fin,@Query(value = "form") String form,
                                       @Query(value = "json") int json);


    @GET("archive/now/{path}.ar")
    Call <DynamicListObject> getDynamicListByPath(@Path("path") String path);

    @GET("quick_search.php")
    Call <SearchObject> getSearchList( @Query(value = "query") String text, @Query(value = "json") int json);

    @GET("abiturient.php")
    Call <StudentInfoObject> getAbiturient(@Query(value = "id") String id);
}
