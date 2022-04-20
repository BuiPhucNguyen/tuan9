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
import com.google.firebase.database.FirebaseDatabase;

public class Rigister extends AppCompatActivity {
    TextView txtNameSU,txtEmailSU,txtPass1SU,txtPass2SU;
    Button btnRegisterSU;

    private FirebaseAuth mAuth;
//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    final StorageReference storageRef = storage.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);

        txtNameSU = findViewById(R.id.txtNameSU);
        txtEmailSU = findViewById(R.id.txtEmailSU);
        txtPass1SU = findViewById(R.id.txtPass1SU);
        txtPass2SU = findViewById(R.id.txtPass2SU);
        btnRegisterSU = findViewById(R.id.btnSignInSI);
        mAuth = FirebaseAuth.getInstance();

        btnRegisterSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNameSU.getText().length()==0){
                    txtNameSU.setError("Please enter your name!");
                }else if(txtEmailSU.getText().length()==0){
                    txtEmailSU.setError("Please enter your email!");
                }else if(txtPass1SU.getText().length()==0){
                    txtPass1SU.setError("Please enter your password!");
                } else if(txtPass2SU.getText().length()==0){
                    txtPass2SU.setError("Please enter your password!");
                } else if(txtPass2SU.getText().equals(txtPass1SU.getText())){
                    txtPass2SU.setError("Two passworđ are not th same!");
                }else{
                    Register();
                }

            }
        });
    }
    private void Register(){
        final String name = txtNameSU.getText().toString();
        String password = txtPass2SU.getText().toString();
        final String email = txtEmailSU.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        txtNameSU.setText("");
                                        txtEmailSU.setText("");
                                        txtPass1SU.setText("");
                                        txtPass2SU.setText("");
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        Toast.makeText(Rigister.this, "Register Successfull!!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Rigister.this, "Fail! Please check internet!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            
                        }else{
                            Toast.makeText(Rigister.this, "Register Unsuccessfull!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}