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

public class LoginActivity extends AppCompatActivity {

    Button logged,mregister;
    EditText lemail;
    EditText lpass;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        lemail=(EditText)findViewById(R.id.emaileditlogin);
        lpass=(EditText)findViewById(R.id.passeditlogin);
        logged=(Button)findViewById(R.id.btnlogin);
        firebaseAuth= FirebaseAuth.getInstance();
        mregister=(Button)findViewById(R.id.regbtn);

        logged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "on process..", true);

                (firebaseAuth.signInWithEmailAndPassword(lemail.getText().toString(), lpass.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged on", Toast.LENGTH_LONG).show();
                            Intent jobintent=new Intent(LoginActivity.this,ProblemActivity.class);
                            startActivity(jobintent);
                        }
                        else
                        {
                            Log.e("error..",task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        });


        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}