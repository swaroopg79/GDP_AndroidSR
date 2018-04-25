package com.example.s528802.studentregistartion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadImages extends AppCompatActivity implements View.OnClickListener {
    Button btnpic;
    ImageView disasterImage;
    private static final int CAM_REQUEST=1;
    Button buttonLogout;
    FirebaseAuth firebaseAuth;
    TextView textViewUserEmail;
    Button uploadImage;
    private Uri imgUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    public static final String FB_STORAGE_PATH="Reports/";
    public static final String FB_DATABASE_PATH="reports";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

       textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome "+user.getEmail());

        btnpic = (Button) findViewById(R.id.button);
        disasterImage = (ImageView)findViewById(R.id.imageView);
        uploadImage = (Button)findViewById(R.id.buttonUpload2);
        btnpic.setOnClickListener(new btnTakePhotoClicker());
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        mStorageRef= FirebaseStorage.getInstance().getReference();
    mDatabaseRef= FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
}



    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path= MediaStore.Images.Media.insertImage(inContext.getContentResolver(),inImage,"Title",null);
        return Uri.parse(path);
    }

    class btnTakePhotoClicker implements  Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAM_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST){

            // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgUri = data.getData();
            Log.d("TAG","+"+imgUri);
            try {
                Toast.makeText(getApplicationContext(), "Outside", Toast.LENGTH_SHORT).show();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                disasterImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Inside ", Toast.LENGTH_SHORT).show();

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            // imgUri = getImageUri(getApplicationContext(),bitmap);


        }
    }
    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));
        }
    }



//    @SuppressWarnings("VisibleForTests")
//    public void uploadButton(View v)
//    {
//
//
//            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis());
//
//            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
//                    ImageUpload imageUpload = new ImageUpload(textViewUserEmail.getText().toString(), taskSnapshot.getDownloadUrl().toString());
//                    String uploadID = mDatabaseRef.push().getKey();
//                    mDatabaseRef.child(uploadID).setValue(imageUpload);
//
//                }
//            })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//        }


    }

