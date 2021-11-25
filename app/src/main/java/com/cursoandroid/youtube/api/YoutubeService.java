package com.cursoandroid.youtube.api;

import com.cursoandroid.youtube.model.Resultado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    @GET("search")
    Call<Resultado>recuperarVideos(
                    @Query("part") String part,
                    @Query("order") String order,
                    @Query("maxResults") String maxResults,
                    @Query("channelId") String channelId,
                    @Query("key") String key
    );

}
