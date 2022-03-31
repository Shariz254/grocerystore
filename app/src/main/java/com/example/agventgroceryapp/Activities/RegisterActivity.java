package com.example.agventgroceryapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agventgroceryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText EregEmail, EregUsername, EregPassword;
    private Button EregisterBtn;
    private TextView EalreadyHvAccQn;
    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EregEmail = findViewById(R.id.regEmail);
        EregUsername = findViewById(R.id.regUsername);
        EregPassword = findViewById(R.id.regPassword);
        EregisterBtn = findViewById(R.id.registerBtn);
        EalreadyHvAccQn = findViewById(R.id.alreadyHvAccQn);
        loader = new ProgressDialog(this);
        act = this;

        mAuth = FirebaseAuth.getInstance();

        EregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = EregEmail.getText().toString().trim();
                final String username = EregUsername.getText().toString().trim();
                final String password = EregPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    EregEmail.setError("Email required");
                    return;
                }

                if (TextUtils.isEmpty(username)){
                    EregUsername.setError("Username required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    EregPassword.setError("Password required");
                    return;
                } else {
                    loader.setMessage("Registering you..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();


                    //FIREBASE REGISTRATION
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                String error = task.getException().toString();
                                Toast.makeText(act, "Error here=>" + error, Toast.LENGTH_SHORT).show();
                            } else {
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

                                HashMap userInfo = new HashMap();
                                userInfo.put("id", currentUserId);
                                userInfo.put("email", email);
                                userInfo.put("username", username);

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(act, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(act, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
                                    }
                                });

                            }
                        }
                    });

                    Intent intent = new Intent(act, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    loader.dismiss();
                }


            }
        });
    }
}