package com.anutejpoddaturi.orgdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class PostActivity extends AppCompatActivity {

    private ImageButton mselectimage;
    private EditText mpostlocation;
    private EditText mpostdesc;
    private Button msubmit;

    private static final int GALLERY_REQUEST = 1;

    private Uri mimageuri = null;
    private StorageReference mstorage;


    private DatabaseReference mdatabase;
    private Context c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        c = this;

        mstorage = FirebaseStorage.getInstance().getReference();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Problem");

        mselectimage = (ImageButton) findViewById(R.id.imageselect);
        mpostlocation = (EditText) findViewById(R.id.locationfield);
        mpostdesc = (EditText) findViewById(R.id.probfield);
        msubmit = (Button) findViewById(R.id.submit);

        mprogress = new ProgressDialog(this);

        mselectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, GALLERY_REQUEST);

            }
        }
        );


        msubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {
        StorageReference filepath;

        mprogress.setMessage("Posting");
        mprogress.show();

        final String loc_val = mpostlocation.getText().toString().trim();
        final String problem = mpostdesc.getText().toString().trim();

        if(TextUtils.isEmpty(loc_val)){

            Toast.makeText(getApplicationContext(),"Please Enter a problem Location name",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(problem)){

            Toast.makeText(getApplicationContext(),"Please Enter a Description for a problem",Toast.LENGTH_SHORT).show();

        }
        else if (!TextUtils.isEmpty(loc_val) && !TextUtils.isEmpty(problem) ) {
            //adding images to a sub directory Event_Images with the name of the image itself
            StorageReference filePath =mstorage.child("Event_Images/"+mimageuri.getLastPathSegment());
            final DatabaseReference newEvent=mdatabase.push();


            //setting up the progress dialog message
            mprogress.setMessage("Adding Event Request");
            mprogress.show();






            filePath.putFile(mimageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    //getDownloadUrl() can only be used in private or test scope so we are suppressing test warnings
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();


                    //storing event data in real time DB

                    newEvent.child("Location").setValue(loc_val);
                    newEvent.child("desc").setValue(problem);

                    newEvent.child("image").setValue(downloadUrl.toString());


                    //if user signed in
                    if(FirebaseAuth.getInstance().getCurrentUser() != null)
                    {
                        newEvent.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        newEvent.child("userName").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    }

                    else
                    {
                        newEvent.child("uid").setValue("guest");
                        newEvent.child("userName").setValue("guest user name");
                    }




                    mprogress.dismiss();
                    Toast.makeText(getApplicationContext(), "Problem added", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "Failed to add Event Request "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


            //after completing posting we return back to the main activity
            Intent main=new Intent(PostActivity.this,ProblemActivity.class);

            startActivity(main);
        }

            /*filepath = mstorage.child("Post_Image").child(mimageuri.getLastPathSegment());
            Toast.makeText(this,"IF lopala",Toast.LENGTH_LONG).show();

            filepath.putFile(mimageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                @SuppressWarnings("VisibleorTests")
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(c,"On Success",Toast.LENGTH_LONG).show();

                    Uri download = taskSnapshot.getDownloadUrl();

                    DatabaseReference newpost = mdatabase.push();
                    newpost.child("image").setValue(download.toString());

                    newpost.child("location").setValue(loc_val);
                    newpost.child("Problem").setValue(problem);


                    mprogress.dismiss();

                    startActivity(new Intent(PostActivity.this, ProblemActivity.class));

                }
            });

        }*/


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this,"ONActivityResult",Toast.LENGTH_LONG).show();
            mimageuri = data.getData();
            mselectimage.setImageURI(mimageuri);
        }
    }
}











