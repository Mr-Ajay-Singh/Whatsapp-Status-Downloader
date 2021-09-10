package com.cyris.StatusDownloader.ui.FileOperation;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class GetFile {
    static ArrayList<String> imageSourceList = new ArrayList<>();

   static ArrayList<String> videoSourceList = new ArrayList<>();

   static ArrayList<String> imageVideoSourceList = new ArrayList<>();


    public static ArrayList<String> setImageSource() {

        try {
            imageSourceList.clear();
            File imageFile = new File(Environment.getExternalStorageDirectory(), "Android");
            if (imageFile.isDirectory()) {
                File status = new File(imageFile + "/media/com.whatsapp/WhatsApp" + "/Media" + "/.Statuses");
                if (status.isDirectory()) {
                    File[] allData = status.listFiles();
                    if (allData.length > 0) {

                        for (int i = 0; i < allData.length; i++) {
                            if (allData[i].getAbsolutePath().endsWith(".jpg"))
                                imageSourceList.add(allData[i].getAbsolutePath());

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageSourceList;
    }





   static public ArrayList<String> setVideoSource() {


       try {
           videoSourceList.clear();
           File imageFile = new File(Environment.getExternalStorageDirectory(), "Android");
           if (imageFile.isDirectory()) {
               File status = new File(imageFile + "/media/com.whatsapp/WhatsApp" + "/Media" + "/.Statuses");
               if (status.isDirectory()) {
                   File[] allData = status.listFiles();
                   if (allData.length > 0) {

                       for (int i = 0; i < allData.length; i++) {
                           if (allData[i].getAbsolutePath().endsWith(".mp4"))
                               videoSourceList.add(allData[i].getAbsolutePath());

                       }
                   }
               }
           }
       }catch (Exception e){
           e.getStackTrace();
       }


        return videoSourceList;
    }






    static public ArrayList<String> setImageVideoSource()
    {


            try {
                imageVideoSourceList.clear();
                File imageFile = new File(Environment.getExternalStorageDirectory(), "StatusSaver");
                if (imageFile.isDirectory()) {

                    File[] allData = imageFile.listFiles();
                    if (allData.length > 0) {

                        for (int i = 0; i < allData.length; i++) {
                            if (allData[i].getAbsolutePath().endsWith(".mp4") || allData[i].getAbsolutePath().endsWith(".jpg"))
                                imageVideoSourceList.add(allData[i].getAbsolutePath());

                        }
                    }

                }
            }catch (Exception e){
                e.getStackTrace();
            }





        return imageVideoSourceList;
    }
}
