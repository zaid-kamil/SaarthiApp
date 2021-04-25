package com.example.sarthi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //  getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        // ------------------------ Go to Sign up Layout ----------------------------

        Button btnToSignup = findViewById(R.id.intenttosignup);

        btnToSignup.setOnClickListener(v -> {
            Intent goToSignup = new Intent(v.getContext(), SignUp.class);
            v.getContext().startActivity(goToSignup); });
        // --------------------------------------------------------------------------


        //-------------------------- Login and go to Home ---------------------------

        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> login());

        //---------------------------------------------------------------------------
    }


    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getUid().equals("OSJvcLc0eJfpYLWjsJ2TU1ybubT2")){
                Intent goToSignaler = new Intent(MainActivity.this, Signaler.class);
                startActivity(goToSignaler);
            }else {
                Intent goToHome = new Intent(MainActivity.this, Home.class);
                startActivity(goToHome);
            }

            if (!(currentUser.isEmailVerified())){
                mAuth.signOut();
                Intent goToHome1 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(goToHome1);
            }
        }
    }




    private void login(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Logged In");

        EditText mailLogin = findViewById(R.id.mailLogin);
        String Email = mailLogin.getText().toString();

        EditText passwordLogin = findViewById(R.id.passwordLogin);
        String Password = passwordLogin.getText().toString();

        TextView ErrorText = findViewById(R.id.errortext2);


        if (!(Email.isEmpty()) && !(Password.isEmpty())){
            ErrorText.setVisibility(View.INVISIBLE);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(MainActivity.this, task -> {
                        if (task.isSuccessful()){

                            verifyEmailAddress();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_LONG).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 1000);

                        }
                    });
        }else {
            ErrorText.setVisibility(View.VISIBLE);
        }
    }

    private void verifyEmailAddress(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Logged In");

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser.isEmailVerified()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 1000);

            Intent goToHome = new Intent(MainActivity.this, Home.class);
            startActivity(goToHome);

        }else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 1000);
            Toast.makeText(getApplicationContext(),"Please verify your account",Toast.LENGTH_LONG).show();

        }

    }

}