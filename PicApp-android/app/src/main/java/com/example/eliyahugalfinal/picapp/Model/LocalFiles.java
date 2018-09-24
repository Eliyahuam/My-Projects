package com.example.eliyahugalfinal.picapp.Model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by eliba on 30/03/2017.
 */

public class LocalFiles {

    public void saveImageToFile(Bitmap theImage,String pictureName) throws IOException {
        try {
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdir();
        }
        File imageFile = new File(pictureDirectory,pictureName);
        imageFile.createNewFile();

        OutputStream out = new FileOutputStream(imageFile);
        theImage.compress(Bitmap.CompressFormat.JPEG,100,out);
        out.close();

        addPicureToGallery(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addPicureToGallery(File imageFile){
        //add the picture to the gallery so we dont need to manage the cache size

        Uri contentUri = Uri.fromFile(imageFile);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
        AppContext.getAppContext().sendBroadcast(mediaScanIntent);

    }
    public Bitmap loadImageFromFile(String imageFileName){
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);

            //File dir = context.getExternalFilesDir(null);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
