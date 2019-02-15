package com.anutejpoddaturi.orgdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {


    Button mLogOutBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);

        mLogOutBtn=(Button)findViewById(R.id.logOutBtn);
        mAuth=FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();




        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAuth.getCurrentUser() != null) {
                    mAuth.signOut();


                    Intent signinIntent = new Intent(SettingActivity.this, SignupActivity.class);
                    signinIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signinIntent);
                }


            }
        });
    }
}
