package com.example.authentication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText ed_user, ed_email, ed_pass;
    Button bt_sign_up;
    TextView tv_sign_in;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_user = findViewById(R.id.user);
        ed_email = findViewById(R.id.email);
        ed_pass = findViewById(R.id.password);
        bt_sign_up = findViewById(R.id.btn_signup);
        tv_sign_in = findViewById(R.id.tv_signin);
        progressBar = findViewById(R.id.progressbar);


        mAuth = FirebaseAuth.getInstance();


        tv_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignInAcitivity.class);
                startActivity(intent);
            }
        });

        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadData();
            }
        });

    }



    private void UploadData() {

        String name = ed_user.getText().toString();
        String email = ed_email.getText().toString();
        String pass = ed_pass.getText().toString();
        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = mAuth.getCurrentUser();
                                updateUI(user);
                                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,Home.class));
                                progressBar.setVisibility(View.INVISIBLE);
                            }else {

                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
        } else {
            Toast.makeText(this, "Please Fill All the Details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


    }


    @SuppressLint("ResourceType")
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
           // Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Home.class));
            finish();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}