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
import android.widget.Toast;

import com.example.agventgroceryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText emailLogin, passwordLogin;
    Activity act;
    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         Auth state listener to enable user to be kept logged in
         **/
        mAuth = FirebaseAuth.getInstance();
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = mAuth.getCurrentUser();
//                if (user != null){
//                    Intent intent = new Intent(act, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };


        act = this;
        loader = new ProgressDialog(this);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailLogin.getText().toString().trim();
                final String password = passwordLogin.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailLogin.setError("Email Required");
                }

                if (TextUtils.isEmpty(password)){
                    passwordLogin.setError("Email Required");
                } else {
                    loader.setMessage("Logging you in..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(act, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(act, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(act, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                            loader.dismiss();
                        }
                    });
                }

            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mAuth.removeAuthStateListener(authStateListener);
//    }
}