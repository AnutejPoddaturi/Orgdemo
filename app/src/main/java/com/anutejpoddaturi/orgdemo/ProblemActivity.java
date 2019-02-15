package com.anutejpoddaturi.orgdemo;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class ProblemActivity extends AppCompatActivity {

    //private FirebaseAuth mAuth; 
    private RecyclerView mproblist;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        //mAuth=FirebaseAuth.getInstance();
        mproblist=(RecyclerView)findViewById(R.id.problist);
        mproblist.setHasFixedSize(true);
        mproblist.setLayoutManager(new LinearLayoutManager(this));

        mdatabase= FirebaseDatabase.getInstance().getReference().child("Problem");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(

                Blog.class,R.layout.prob_row,
                BlogViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                viewHolder.settitle(model.getLocation());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

            }
        };

        mproblist.setAdapter(firebaseRecyclerAdapter);


    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mview=itemView;


        }
        public void settitle(String title)
        {
            TextView post_title=(TextView) mview.findViewById(R.id.post_title);
            post_title.setText(title);

        }

        public void setDesc(String desc)
        {
            TextView post_desc=(TextView) mview.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setImage( Context ctx ,String image)
        {
            ImageView post_image=(ImageView) mview.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()  == R.id.add){

            startActivity(new Intent(ProblemActivity.this,PostActivity.class));

        }
        else if(item.getItemId() == R.id.action_settings){

            Intent pro=new Intent(ProblemActivity.this,LoginActivity.class);
            startActivity(pro);

        }

        return super.onOptionsItemSelected(item);

    }
}

