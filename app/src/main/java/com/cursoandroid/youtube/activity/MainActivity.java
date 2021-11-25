package com.cursoandroid.youtube.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toolbar;

import com.cursoandroid.youtube.R;
import com.cursoandroid.youtube.adapter.AdapterVideo;
import com.cursoandroid.youtube.api.YoutubeService;
import com.cursoandroid.youtube.helper.RecyclerItemClickListener;
import com.cursoandroid.youtube.helper.RetrofitConfig;
import com.cursoandroid.youtube.helper.YoutubeConfig;
import com.cursoandroid.youtube.model.Item;
import com.cursoandroid.youtube.model.Resultado;
import com.cursoandroid.youtube.model.Video;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;
    private MaterialSearchView searchView;

    private List<Item> videos = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;

    // Retrofit
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicializar componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);


        // Configurações iniciais
        retrofit = RetrofitConfig.getRetrofit();


        // Configura toolbar
        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle( "Youtube" );
        setActionBar( toolbar );


        // Recupera videos
        recuperarVideos();


        // Configura SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem( item );

        return true;
    }

    private void recuperarVideos(){

        YoutubeService youtubeService = retrofit.create( YoutubeService.class );
        youtubeService.recuperarVideos(
                "snippet",
                "date",
                "20",
                YoutubeConfig.CANAL_ID,
                YoutubeConfig.CHAVE_API
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado", "resultado: " + response.toString() );
                if( response.isSuccessful() ){
                    resultado = response.body();
                    videos = resultado.items;
                    configurarRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });

    }

    public void configurarRecyclerView(){

        adapterVideo = new AdapterVideo(videos, getApplicationContext());
        recyclerVideos.setHasFixedSize( true );
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter( adapterVideo );

        recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Item video = videos.get( position );
                                String idvideo = video.id.videoId;

                                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                                i.putExtra( "id", idvideo );
                                startActivity( i );
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }
}