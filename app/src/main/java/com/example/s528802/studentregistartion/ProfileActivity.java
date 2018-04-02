package com.example.s528802.studentregistartion;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;


    //view objects
    private EditText editDisasterName;
    private EditText editCriticality;
    ImageView disasterImage;
    private Button buutonUploadImage;
    private Button chooseButtonDisaster;
    private Button buttonLogout;


    // Folder path for Firebase Storage.
    String Storage_Path = "Disaster_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "Student_Database/";
// Have to include all the functionalities for uploading photo from camera
// Creating URI.
    Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.
    StorageReference disasterStorageReference;
    DatabaseReference disasterDatabaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    // private ProgressDialog progressDialog;


    //defining firebaseauth object



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        disasterStorageReference = FirebaseStorage.getInstance().getReference();
        disasterDatabaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        chooseButtonDisaster = (Button)findViewById(R.id.ButtonChooseImage);
        disasterImage = (ImageView)findViewById(R.id.ShowImageView);


        chooseButtonDisaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        editDisasterName = (EditText) findViewById(R.id.disasterName);
        editCriticality = (EditText) findViewById(R.id.criticality);
        buutonUploadImage = (Button) findViewById(R.id.uploadDisasterImage);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        buutonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to upload selected image on Firebase storage.
                // UploadImageFileToFirebaseStorage();
               // registerUser();

            }
        });

        //adding listener to button
       buttonLogout.setOnClickListener(this);

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.



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
