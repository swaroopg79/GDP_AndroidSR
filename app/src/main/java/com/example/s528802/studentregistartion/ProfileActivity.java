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
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            //  progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            //   progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String TempFName = fname.getText().toString().trim();
                            String TempLName = lname.getText().toString().trim();
                            String TempEmail = editTextEmail.getText().toString().trim();
                            String TempPassword = editTextPassword.getText().toString().trim();
                            // Hiding the progressDialog after done uploading.
                            // progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "User Registered Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            DataUploadInfo imageUploadInfo = new DataUploadInfo(TempFName,TempLName,TempEmail,TempPassword,taskSnapshot.getDownloadUrl().toString());
                            //System.out.println("I am inside upload Image method");
                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            // progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            // progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(MainActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

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
}
