package com.anutejpoddaturi.orgdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private Button register;
    private EditText semail;
    private EditText spass;
    private EditText sname;
    private Button adduser,lgnbtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        semail=(EditText)findViewById(R.id.emailedit);
        spass=(EditText)findViewById(R.id.passedit);
        sname=(EditText)findViewById(R.id.nameedit);
        lgnbtn=(Button)findViewById(R.id.btnlgn);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        register=(Button) findViewById(R.id.btnreg);
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(SignupActivity.this, "Please wait...", "on process..", true);
                (firebaseAuth.createUserWithEmailAndPassword(semail.getText().toString(), spass.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Sign in Successfull", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Log.e("error..",task.getException().toString());
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }

        });

        lgnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logon=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(logon);
            }
        });
    }
}

