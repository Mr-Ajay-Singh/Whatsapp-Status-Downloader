package com.cyris.StatusDownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import com.cyris.StatusDownloader.ui.FileOperation.*;

public class LoadVideo extends AppCompatActivity {

    SimpleExoPlayer player;
    PlayerView playerView;
    ArrayList<String> videoList,video2;
    int position,downloadPosition,count=0;
    ImageView downloadVideo,shareVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_video);
        getSupportActionBar().hide();
        downloadVideo = findViewById(R.id.downloadVideoInPlayer);
        videoList = new ArrayList<>();
        video2 = getIntent().getStringArrayListExtra("videoIntent");
        position = getIntent().getIntExtra("position",0);
        playerView = findViewById(R.id.playerInVideo);
        shareVideo = findViewById(R.id.shareVideoInPlayer);
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        for(int i=0;i<video2.size();i++)
        {
            if(video2.get(i).endsWith(".mp4"))
            {
              videoList.add(video2.get(i));
                count++;
                if(video2.get(position)==videoList.get(count-1))
                    position=count-1;
            }
        }



        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,"WhatsappStatuDownload"));
        MediaSource mediaSource;
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
        for(int i=0;i<videoList.size();i++)
        {

                Uri uri = Uri.parse(videoList.get(i));
                mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
                concatenatingMediaSource.addMediaSource(mediaSource);


        }

        playerView.setPlayer(player);
        player.setPlayWhenReady(true);

        player.prepare(concatenatingMediaSource,false,false);
        player.seekTo(position,1);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPositionDiscontinuity(int reason) {
                position = player.getCurrentWindowIndex();
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }
        });

      //  player.setPlayWhenReady(true);


        downloadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Toast.makeText(LoadVideo.this,"Video Saved",Toast.LENGTH_SHORT).show();
                    DownloadFile.DownloadFile(videoList.get(position),getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        shareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("video/*");
                intent.setPackage("com.whatsapp");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(videoList.get(position)));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
