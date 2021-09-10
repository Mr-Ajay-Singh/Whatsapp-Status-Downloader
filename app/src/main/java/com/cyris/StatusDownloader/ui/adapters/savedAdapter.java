package com.cyris.StatusDownloader.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.cyris.StatusDownloader.LoadImage;
import com.cyris.StatusDownloader.LoadVideo;
import com.cyris.StatusDownloader.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.cyris.StatusDownloader.ui.FileOperation.*;

public class savedAdapter extends RecyclerView.Adapter<savedAdapter.SavedViewHolder> {

    Context context;
    ArrayList<String> imageVideoList;
    ArrayList<String> imageList,videoList;
    public AlertDialog.Builder dialog;
    ShowInterface showInterface;

    int imageCount=0;

    public savedAdapter(Context ctx)
    {
        this.context = ctx;
        if(imageVideoList==null)
            imageVideoList = GetFile.setImageVideoSource();
        imageList = new ArrayList<>();
        videoList = new ArrayList<>();
        notifyDataSetChanged();
        insertMethod();
        dialog = new AlertDialog.Builder(context).setTitle("Do You Want To Delete")
                .setMessage("Item will be deleted from Saved");
        dialog.create();
    }

    public savedAdapter(Context ctx,ShowInterface showInterface1)
    {
        this.context = ctx;
            imageVideoList = GetFile.setImageVideoSource();
        imageList = new ArrayList<>();
        videoList = new ArrayList<>();
        notifyDataSetChanged();
        insertMethod();
        dialog = new AlertDialog.Builder(context).setTitle("Do You Want To Delete")
                .setMessage("Item will be deleted from Saved");
        dialog.create();
        this.showInterface = showInterface1;
    }

    private void insertMethod() {
        for (int i=0;i<imageVideoList.size();i++) {
            if (imageVideoList.get(i).endsWith(".jpeg")) {
                imageList.add(imageVideoList.get(i));
            } else if (imageVideoList.get(i).endsWith(".mp4")) {
                videoList.add(imageVideoList.get(i));
            }
        }
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_holder,parent,false);


        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        if(imageVideoList.get(position).endsWith(".jpg"))
        {
            holder.LoadImage(imageVideoList.get(position));
            holder.ImageClick(imageCount,position);

        }
        else if(imageVideoList.get(position).endsWith(".mp4"))
        {

            holder.LoadVideoImage(imageVideoList.get(position));
            holder.VideoImageClick(position);

        }
    }

    @Override
    public int getItemCount() {
        if(imageVideoList.size()==0)
            showInterface.ShowDialog();
        else
            showInterface.HideDialog();
        return imageVideoList.size();
    }

    class SavedViewHolder extends RecyclerView.ViewHolder {

        ImageView mainImage,imageSymbol,downloadImage;
        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.imageInVideoHolder);
            imageSymbol = itemView.findViewById(R.id.videoSymbol);
            downloadImage = itemView.findViewById(R.id.downloadInVideoHolder);
            downloadImage.setVisibility(View.INVISIBLE);
        }

        public void LoadImage(String uri)
        {
            Uri uri1 = Uri.parse(uri);
            mainImage.setImageURI(uri1);
            imageSymbol.setVisibility(View.INVISIBLE);
            AnimationSet animationSet = new AnimationSet(context,null);
            animationSet.addAnimation(new AlphaAnimation((float)0.1,(float)0.9));
            animationSet.addAnimation(new TranslateAnimation(-mainImage.getWidth(),0,0,0));
            animationSet.setDuration(300);
            mainImage.setAnimation(animationSet);
            File file = new File(uri);
            Picasso.get().load(file).resize(300,300).into(mainImage);
            // Glide.with(context).load(uri1).into(imageInImageHolder);
            // Glide.with(context).load(uri).override(300,300).into(mainImage);
        }

        public void ImageClick(final int pos,final int deletePosition) {
            mainImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoadImage.class);
                    intent.putExtra("imageIntent", imageVideoList);
                    intent.putExtra("position", deletePosition);
                    context.startActivity(intent);
                }
            });

            mainImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();

                                DeleteFile.DeleteFile(imageVideoList.get(deletePosition),context);
                                imageVideoList.remove(deletePosition);
                                notifyDataSetChanged();

                        }
                    });
                    dialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }

        public void LoadVideoImage(String uri)
        {
            Uri uri1 = Uri.parse(uri);
            imageSymbol.setVisibility(View.VISIBLE);
           // Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(uri, MediaStore.Video.Thumbnails.MICRO_KIND);
           // mainImage.setImageBitmap(bitmap);
            //imageInVideoHolder.setImageURI(uri1);
            AnimationSet animationSet = new AnimationSet(context,null);
            animationSet.addAnimation(new AlphaAnimation((float)0.1,(float)0.9));
            animationSet.addAnimation(new TranslateAnimation(-mainImage.getWidth(),0,0,0));
            animationSet.setDuration(300);
            mainImage.setAnimation(animationSet);
             Glide.with(context).asBitmap().load(uri1).centerCrop().thumbnail(Glide.with(context)
                     .asBitmap()
                     .load(uri)
                     .centerCrop()
                     .override(200,200)
                     .thumbnail(0.5f)).into(mainImage);
            // Picasso.with(context).load(uri).into(imageInImageHolder);
        }

        public void VideoImageClick(final int pos) {
            mainImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoadVideo.class);
                    intent.putExtra("videoIntent",imageVideoList);
                    intent.putExtra("position",pos);
                    context.startActivity(intent);
                }
            });

            mainImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();

                                DeleteFile.DeleteFile(imageVideoList.get(pos),context);
                                imageVideoList.remove(pos);
                                notifyDataSetChanged();


                        }
                    });
                    notifyDataSetChanged();
                    dialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });

        }
    }
   public interface ShowInterface
    {
       void ShowDialog();
       void HideDialog();
    }
}
