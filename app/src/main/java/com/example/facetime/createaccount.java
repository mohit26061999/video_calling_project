package com.example.facetime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class createaccount extends AppCompatActivity {

    private FirebaseAuth auth;
    FirebaseFirestore database;
    private EditText emailbox, passwordbox, namebox;
    private Button loginbtn, createbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createaccount);
        database=FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        emailbox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);
        namebox = findViewById(R.id.namebox);
        loginbtn = findViewById(R.id.loginbtn);
        createbtn = findViewById(R.id.createbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivities(new Intent[]{new Intent(createaccount.this,LoginActivity.class)});
            }
        });

        createbtn.setOnClickListener(new View.OnClickListener() { // Set this for createbtn instead of loginbtn
            @Override
            public void onClick(View view) {
                String email = emailbox.getText().toString();
                String pass = passwordbox.getText().toString();
                String name = namebox.getText().toString();
                user User=new user();
                User.setEmail(email);
                User.setPass(pass);
                User.setName(name);

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            database.collection("users")
                                            .document().set(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivities(new Intent[]{new Intent(createaccount.this,LoginActivity.class)});
                                        }
                                    });
                            Toast.makeText(createaccount.this, "Account is Created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(createaccount.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
