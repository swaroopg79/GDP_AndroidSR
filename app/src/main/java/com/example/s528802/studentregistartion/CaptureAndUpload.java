package com.example.s528802.studentregistartion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class CaptureAndUpload extends AppCompatActivity {

    Button mUploadbtn;
    ImageView mImageview;

    static final int CAM_REQUEST=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUploadbtn=(Button) findViewById(R.id.uploadButton);
        mImageview = (ImageView) findViewById(R.id.dimage);


    }


    public void capture(View v)
    {
        Intent camera_intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file=getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent,CAM_REQUEST);
    }
    private File getFile()
    {

        File folder=new File("sdcard/camera_app");

        if(!folder.exists())
        {
            folder.mkdir();
        }
        File image_file=new File(folder,"cam_image.jpg");
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path="sdcard/camera_app/cam_image.jpg";
        mImageview.setImageDrawable(Drawable.createFromPath(path));
    }



}
