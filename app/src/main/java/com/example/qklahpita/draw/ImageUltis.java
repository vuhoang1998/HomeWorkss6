package com.example.qklahpita.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by admin on 2/4/2018.
 */

public class ImageUltis {
    private static final String TAG = "ImageUltis";

    public static String folderName = "DrawImage";
    private static File tempFile;

    public static void saveImage(Bitmap bitmap, Context context) {
        //1. create new folder to save image
        String root = Environment.getExternalStorageDirectory().toString();
        Log.d(TAG, "saveImage: " + root);

        File folder = new File(root, folderName);
        folder.mkdirs();

        //2. create empty file(.png)
        String imageName = Calendar.getInstance().getTime().toString() + ".png";
        Log.d(TAG, "saveImage: " + imageName);
        File imageFile = new File(folder.toString(), imageName);

        //3. use fileOutputStream to save image

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();

            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ImageModel> getListImage(){
        List<ImageModel> imageModelList = new ArrayList<>();

        File folder = new File(Environment
                .getExternalStorageDirectory().toString(), folderName);
        File[] listImage = folder.listFiles();
        if( listImage != null ) {
            for (int i = 0; i < listImage.length; i++) {
                String path = listImage[i].getAbsolutePath();
                String name = listImage[i].getName();

                ImageModel imageModel = new ImageModel(name,path);

                imageModelList.add(imageModel);
            }

        }
        return imageModelList;
    }

    public static void deleteImage(int pos, Context context) {
        Log.d(TAG, "deleteImage: " + pos);
        File folder = new File(Environment.getExternalStorageDirectory().toString(), folderName);
        File[] listImage = folder.listFiles();
        listImage[pos].delete();

        //noti
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        


    }

    public static Uri getUri(Context context){
        //create file
        try {
            tempFile = File.createTempFile(
                    Calendar.getInstance().getTime().toString(),
                    "jpg",
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            );
            tempFile.deleteOnExit();
        } catch(IOException e){
            e.printStackTrace();
        }

        //get uri
        Uri uri = null;
        uri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider",
                tempFile
        );
        Log.d(TAG, "getUri: "+ uri);
        return uri;
    }

    public static Bitmap getBitmap(Context context){
        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getPath());

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        double ratio = (double) bitmap.getWidth() / bitmap.getHeight() ;

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(
                bitmap,
                screenWidth,
                (int) (screenWidth/ratio),
                false);

        return scaledBitmap;
    }
}
