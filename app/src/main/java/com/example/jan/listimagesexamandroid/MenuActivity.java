package com.example.jan.listimagesexamandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Calendar;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class MenuActivity extends AppCompatActivity {

    ImageView i;
    Button goBtn;
    Button photoBtn;
    Button pauseBtn;
    Bitmap bitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri filePath;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Initializing vars
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bensoundukulele);
        mediaPlayer.start();
        firebaseStorage = FirebaseStorage.getInstance();
        photoBtn = findViewById(R.id.photoBtn);
        goBtn = findViewById(R.id.goBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        i = findViewById(R.id.img);
        // OnClickListeners
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeImageOnGallery();
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });
        //dispatchTakePictureIntent();
    }

    private void uploadImage() {
        UUID uuid = UUID.randomUUID();
        final StorageReference myRef = firebaseStorage.getReference().child("images/" + uuid.toString() + ".jpg");
        UploadTask uploadTask = myRef.putFile(filePath);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }

                // Continue with the task to get the download URL
                return myRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    storeImageOnDatabase(downloadUri);

                    Intent intent = new Intent(MenuActivity.this, ListImagesActivity.class);
                    intent.putExtra("newImage", downloadUri.toString());
                    startActivity(intent);
                }  // Handle errors

            }
        });
    }

    private void storeImageOnDatabase(Uri downloadUri) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference imagesDatabaseReference = firebaseDatabase.getReference().child("images");
        UUID uuid = UUID.randomUUID();
        DatabaseReference temporalImageDatabaseReference = imagesDatabaseReference.child(uuid.toString());
        temporalImageDatabaseReference.child("imagesUrl").setValue(downloadUri.toString());
        temporalImageDatabaseReference.child("date").setValue(Calendar.getInstance().getTime().toString());
    }

    private void chargeImageOnGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                filePath = data.getData();
                bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i.setImageBitmap(bitmap);
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {

                assert data != null;
                Bundle extras = data.getExtras();
                assert extras != null;
                Bitmap bitmap = (Bitmap) extras.get("data");
                i.setImageBitmap(bitmap);
            }
        }
    }
}
