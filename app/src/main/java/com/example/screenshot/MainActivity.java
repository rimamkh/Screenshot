package com.example.screenshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verfifyStoragePermission(this);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              takescreenShot(getWindow().getDecorView().getRootView(),
                      "result");
            }
        });
    }
    protected static File takescreenShot(View view, String filename){
        Date date=new Date();
        CharSequence formate= DateFormat.format("yyyy-MM-hh:mm:ss",date);
        try {
            String dirpath= Environment.getExternalStorageDirectory().toString()+
                    "/learn";
            File filedir=new File(dirpath);
            if(!filedir.exists()){
                boolean mkdir=filedir.mkdir();
            }
            String path=dirpath+"/"+filename+"-"+formate+".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap=Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile=new File(path);
            FileOutputStream fileOutputStream=new FileOutputStream(imageFile);
            int quality=100;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return  imageFile;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  null;
    }
    private static  final int REQUEST_EXTERNAL_STORAGE=1;
    private static String[]PermissionStorage={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static void verfifyStoragePermission(Activity activity){
        int permission= ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PermissionStorage,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}