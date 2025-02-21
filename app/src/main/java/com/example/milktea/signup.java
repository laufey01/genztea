package com.example.milktea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milktea.databinding.ActivitySignupBinding;

public class signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.username.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String confirmpassword = binding.confirmpassword.getText().toString();

                // Check if any fields are empty
                if (username.equals("") || email.equals("") || password.equals("") || confirmpassword.equals("")) {
                    Toast.makeText(signup.this, "All Fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if passwords match
                    if (password.equals(confirmpassword)) {
                        // Check if email already exists
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);
                        // Check if username already exists
                        Boolean checkUsername = databaseHelper.checkUsername(username);

                        if (checkUserEmail == false && checkUsername == false) {
                            // Insert data into database
                            Boolean insert = databaseHelper.insertUsersData(username, email, password);

                            if (insert) {
                                Toast.makeText(signup.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(signup.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (checkUserEmail) {
                                Toast.makeText(signup.this, "Email already exists, Please login", Toast.LENGTH_SHORT).show();
                            }
                            if (checkUsername) {
                                Toast.makeText(signup.this, "Username already exists, Please choose another", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
