package com.cyris.StatusDownloader.ui.FileOperation;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class DeleteFile {

    static public void DeleteFile(String source, final Context context) {
            File originalFile = new File(source);
            if (originalFile.exists()) {
                originalFile.delete();
                Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, "File Not Exist", Toast.LENGTH_SHORT).show();
            }

    }
}
