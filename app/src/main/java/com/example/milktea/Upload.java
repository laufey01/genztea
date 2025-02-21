package com.example.milktea; // Replace with your package name

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Upload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Request code for image selection

    private ImageView uploadImage;
    private EditText uploadName, uploadDescription, uploadPrice;
    private Button saveButton;
    private Uri imageUri; // To store the selected image URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload); // Replace with your XML layout file name

        // Initialize views
        uploadImage = findViewById(R.id.uploadimage);
        uploadName = findViewById(R.id.uploadmilktea_name);
        uploadDescription = findViewById(R.id.uploaddescription);
        uploadPrice = findViewById(R.id.uploadprice);
        saveButton = findViewById(R.id.savebutton);

        // Set click listener for the ImageView to select an image
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        // Set click listener for the Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    // Open the image picker (gallery)
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle the result of the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImage.setImageURI(imageUri); // Set the selected image to the ImageView
        }
    }

    // Save the item details
    private void saveItem() {
        String milkteaName = uploadName.getText().toString().trim();
        String description = uploadDescription.getText().toString().trim();
        String price = uploadPrice.getText().toString().trim();

        // Validate input fields
        if (milkteaName.isEmpty() || description.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Add logic to upload the data (e.g., to a database or server)
        // For now, just display a success message
        Toast.makeText(this, "Item saved successfully!", Toast.LENGTH_SHORT).show();

        // Clear the input fields
        uploadName.setText("");
        uploadDescription.setText("");
        uploadPrice.setText("");
        uploadImage.setImageResource(R.drawable.upload); // Reset the image to the default
        imageUri = null; // Clear the image URI
    }
}