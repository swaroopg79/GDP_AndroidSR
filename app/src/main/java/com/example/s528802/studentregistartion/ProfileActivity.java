package com.example.s528802.studentregistartion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;


    //view objects
    private EditText editDisasterName;
    private EditText editCriticality;

     ImageView disasterImage;

     Button buutonUploadImage;
     Button chooseButtonDisastertoDB;

    private Button buttonLogout;

    private static final int CAM_REQUEST=1313;


    private StorageReference mStorage;



    //till up the code is correct
    // Folder path for Firebase Storage.
    String Storage_Path = "Disaster_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "Student_Database/";
// Have to include all the functionalities for uploading photo from camera
// Creating URI.
    Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.

    DatabaseReference disasterDatabaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    // private ProgressDialog progressDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mStorage = FirebaseStorage.getInstance().getReference();
       // disasterDatabaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        chooseButtonDisastertoDB = (Button)findViewById(R.id.cameraUpload);
        disasterImage = (ImageView)findViewById(R.id.ShowImageView);

        buutonUploadImage= (Button)findViewById(R.id.uploadDisasterImage);
        buttonLogout = (Button)findViewById(R.id.buttonLogout);

      //  buutonUploadImage.setOnClickListener(new btnTakePhotoClicker());




        chooseButtonDisastertoDB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

               // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File file=getFile();
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
               // startActivityForResult(intent,CAM_REQUEST);




            }
        });

//        buutonUploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // Creating intent.
//                Intent intent = new Intent();
//
//                // Setting intent type as image to select image from phone storage.
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
//
//            }
//        });

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
//        if(firebaseAuth.getCurrentUser() == null){
//            //closing this activity
//            finish();
//            //starting login activity
//            startActivity(new Intent(this, Login.class));
//        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        //editDisasterName = (EditText) findViewById(R.id.disasterName);
        //editCriticality = (EditText) findViewById(R.id.criticality);
      //  buutonUploadImage = (Button) findViewById(R.id.uploadDisasterImage);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        buutonUploadImage.setOnClickListener(new btnTakePhotoClicker());

        //adding listener to button
       buttonLogout.setOnClickListener(this);
//
   }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.

//    private File getFile()
//    {
//
//        File folder=new File("sdcard/camera_app");
//
//        if(!folder.exists())
//        {
//            folder.mkdir();
//        }
//        File image_file=new File(folder,"cam_image.jpg");
//        return image_file;
//    }

    //For loading image to imageview
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if(requestCode == CAM_REQUEST){
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        disasterImage.setImageBitmap(bitmap);
    }

}




    class btnTakePhotoClicker implements  Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAM_REQUEST);
        }
    }










//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//       // String path="sdcard/camera_app/cam_image.jpg";
//       // disasterImage.setImageDrawable(Drawable.createFromPath(path));
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==CAM_REQUEST )
//        {
//            Uri uri = data.getData();
//            Toast.makeText(this, "I am here ", Toast.LENGTH_SHORT).show();
//            Log.d("TAG","Iam here");
//            StorageReference filepath = mStorage.child("images").child(uri.getLastPathSegment());
//
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//
//                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
//                    Picasso.with(ProfileActivity.this).load(downloadUri).fit().centerCrop().into(disasterImage);
//
//                    Toast.makeText(ProfileActivity.this,"Uploading finished...",Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }

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
}
