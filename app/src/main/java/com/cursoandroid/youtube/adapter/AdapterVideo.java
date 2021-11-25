package com.cursoandroid.youtube.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cursoandroid.youtube.R;
import com.cursoandroid.youtube.model.Item;
import com.cursoandroid.youtube.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AdapterVideo extends RecyclerView.Adapter< AdapterVideo.MyViewHolder > {

    private List<Item> videos;
    private Context context;


    public AdapterVideo(List<Item> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video, parent, false);
        return new AdapterVideo.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item video = videos.get( position );

        holder.titulo.setText( video.snippet.title );
        Picasso.get().load(Uri.parse( video.snippet.thumbnails.high.url )).into( holder.capa );

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

         TextView titulo;
         TextView descriçao;
         TextView data;
         ImageView capa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            capa = itemView.findViewById(R.id.imageCapa);
        }
    }
}
