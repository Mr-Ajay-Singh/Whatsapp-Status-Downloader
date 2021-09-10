package com.cyris.StatusDownloader.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cyris.StatusDownloader.LoadVideo;
import com.cyris.StatusDownloader.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.cyris.StatusDownloader.ui.FileOperation.*;

public class videoAdapter extends RecyclerView.Adapter<videoAdapter.VideoViewHolder> {


    ArrayList<String> videoList;
    Context context;
    public videoAdapter(Context ctx)
    {
        this.context = ctx;
        if(videoList==null)
            videoList = GetFile.setVideoSource();
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_holder,parent,false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.LoadImage(videoList.get(position));
        holder.ImageClick(position);
        holder.DownloadClick(position);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageInVideoHolder,downloadButton;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageInVideoHolder = itemView.findViewById(R.id.imageInVideoHolder);
            downloadButton = itemView.findViewById(R.id.downloadInVideoHolder);
        }

        public void LoadImage(String uri)
        {
            Uri uri1 = Uri.parse(uri);
            AnimationSet animationSet = new AnimationSet(context,null);
            animationSet.addAnimation(new AlphaAnimation((float)0.1,(float)0.9));
            animationSet.addAnimation(new TranslateAnimation(-imageInVideoHolder.getWidth(),0,0,0));
            animationSet.setDuration(300);
            imageInVideoHolder.setAnimation(animationSet);
            Glide.with(context).asBitmap().load(uri1).centerCrop().thumbnail( Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .centerCrop()
                    .thumbnail(0.5f)).into(imageInVideoHolder);
            // Picasso.with(context).load(uri).into(imageInImageHolder);
        }

        public void ImageClick(final int pos) {
            imageInVideoHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoadVideo.class);
                    intent.putExtra("videoIntent",videoList);
                    intent.putExtra("position",pos);
                    context.startActivity(intent);
                }
            });

        }

        public void DownloadClick(final int pos)
        {
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context,"Video Saved",Toast.LENGTH_SHORT).show();
                    downloadButton.setClickable(false);
                    try {
                        DownloadFile.DownloadFile(videoList.get(pos),context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
