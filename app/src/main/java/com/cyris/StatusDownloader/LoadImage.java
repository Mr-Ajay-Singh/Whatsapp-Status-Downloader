package com.cyris.StatusDownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.cyris.StatusDownloader.ui.FileOperation.DownloadFile;

import java.io.IOException;
import java.util.ArrayList;

public class LoadImage extends AppCompatActivity {

    ImageView imageInLoadImage,downloadImage,shareImage;
    ArrayList<String> uri;
    int position;
    float x1,x2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        getSupportActionBar().hide();
        uri = getIntent().getStringArrayListExtra("imageIntent");
        position = getIntent().getIntExtra("position",0);


        Uri uri1 = Uri.parse(uri.get(position));
        shareImage = findViewById(R.id.shareImageInImage);
        downloadImage = findViewById(R.id.downloadImageInImage);
        imageInLoadImage = findViewById(R.id.imageInLoadImage);
        imageInLoadImage.setImageURI(uri1);

        downloadImageClick();
        shareImageClick();

        imageInLoadImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() ==MotionEvent.ACTION_DOWN)
                {
                    x1=event.getX();
                    Log.i("posChange2",String.valueOf(x1));
                }
                if(event.getAction()==MotionEvent.ACTION_UP|| event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    x2=event.getX();
                    Log.i("posChange2",String.valueOf(x2-x1));
                    if((x2-x1)>150)
                        CallSwipeRight();
                    else if((x2-x1)<-150)
                        CallSwipeLeft();
                }

                return true;
            }
        });
    }

    private void shareImageClick() {
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpg");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri.get(position)));
                    startActivity(intent);

            }
        });
    }

    private void downloadImageClick() {
        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(LoadImage.this,"Image Downloaded",Toast.LENGTH_SHORT).show();
                    DownloadFile.DownloadFile(uri.get(position),getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void CallSwipeRight() {

        if(position>0)
        {
            boolean flag=false;

            Uri uri1= Uri.parse(uri.get(--position));
            if(uri.get(position).endsWith(".jpg")) {
                AnimationSet animationSet = new AnimationSet(this, null);
                animationSet.addAnimation(new AlphaAnimation((float) 0.1, (float) 0.9));
                animationSet.addAnimation(new TranslateAnimation(-imageInLoadImage.getWidth(), 0, 0, 0));
                animationSet.setDuration(300);
                imageInLoadImage.setAnimation(animationSet);
                imageInLoadImage.setImageURI(uri1);
            }
            else
            {
                while(!uri.get(--position).endsWith(".jpg")&&position>0)
                {
                   flag=true;
                }
                if(uri.get(position).endsWith(".jpg"))
                {
                    flag=false;
                }
                if(!flag)
                {
                    Uri uri2= Uri.parse(uri.get(position));
                    AnimationSet animationSet = new AnimationSet(this, null);
                    animationSet.addAnimation(new AlphaAnimation((float) 0.1, (float) 0.9));
                    animationSet.addAnimation(new TranslateAnimation(-imageInLoadImage.getWidth(), 0, 0, 0));
                    animationSet.setDuration(300);
                    imageInLoadImage.setAnimation(animationSet);
                    imageInLoadImage.setImageURI(uri2);
                }

            }
        }

    }

    private void CallSwipeLeft() {
        if(uri.size()-1>position)
        {
            boolean flag=false;

            if(uri.get(++position).endsWith(".jpg")) {
                Uri uri1 = Uri.parse(uri.get(position));
                AnimationSet animationSet = new AnimationSet(this, null);
                animationSet.addAnimation(new AlphaAnimation((float) 0.1, (float) 0.9));
                animationSet.addAnimation(new TranslateAnimation(imageInLoadImage.getWidth(), 0, 0, 0));
                animationSet.setDuration(300);
                imageInLoadImage.setAnimation(animationSet);
                imageInLoadImage.setImageURI(uri1);
            }
            else
            {
                while(!uri.get(++position).endsWith(".jpg")&&uri.size()-1>position)
                {
                    flag=true;
                }
                if(uri.get(position).endsWith(".jpg"))
                {
                    flag=false;
                }
                if(!flag)
                {
                    Uri uri2= Uri.parse(uri.get(position));
                    AnimationSet animationSet = new AnimationSet(this, null);
                    animationSet.addAnimation(new AlphaAnimation((float) 0.1, (float) 0.9));
                    animationSet.addAnimation(new TranslateAnimation(-imageInLoadImage.getWidth(), 0, 0, 0));
                    animationSet.setDuration(300);
                    imageInLoadImage.setAnimation(animationSet);
                    imageInLoadImage.setImageURI(uri2);
                }
            }


        }
    }
}
