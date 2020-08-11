package com.example.authentication1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Home extends AppCompatActivity {
    Button btn_signOut;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_signOut = findViewById(R.id.signOut);
        mAuth = FirebaseAuth.getInstance();

        try{
            getActionBar().setTitle("Home");
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);

        }catch (NullPointerException e){
            e.printStackTrace();
        }


        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    mAuth.signOut();
                    Toast.makeText(Home.this, "You'r Sign Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Home.this,MainActivity.class));
                }else {
                    Toast.makeText(Home.this, "You were not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}