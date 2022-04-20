package com.example.tuan9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    TextView txtEmailSI,txtPassSI;
    Button btnSignInSI;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtEmailSI = findViewById(R.id.txtEmailSI);
        txtPassSI = findViewById(R.id.txtPassSI);
        btnSignInSI = findViewById(R.id.btnSignInSI);

        mAuth = FirebaseAuth.getInstance();

        btnSignInSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEmailSI.getText().length()==0){
                    txtEmailSI.setError("Please enter your email!");
                }else if(txtPassSI.getText().length()==0){
                    txtPassSI.setError("Please enter your password!");
                }else{
                    LogIn();
                }
            }
        });
    }

    private void LogIn() {
        String password = txtPassSI.getText().toString();
        final String email = txtEmailSI.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(SignIn.this,MainActivity2.class));
                            Toast.makeText(SignIn.this, "Sign in successfull!", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignIn.this, "Sign in unsuccessfull!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}