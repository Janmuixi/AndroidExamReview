package com.example.jan.listimagesexamandroid;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.jan.listimagesexamandroid.Adapters.AdapterImages;
import com.example.jan.listimagesexamandroid.Models.FirebaseImageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListImagesActivity extends AppCompatActivity {
    RecyclerView imagesList;
    ArrayList<String> listUrls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_images);
        listUrls = new ArrayList<>();
        imagesList = findViewById(R.id.imagesListView);
        imagesList.setLayoutManager(new LinearLayoutManager(this));


        final AdapterImages adapter = new AdapterImages(listUrls);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), listUrls.get(imagesList.getChildAdapterPosition(v)), Toast.LENGTH_SHORT).show();
            }
        });
        imagesList.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listUrls.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FirebaseImageModel firebaseImageModel = snapshot.getValue(FirebaseImageModel.class);
                            assert firebaseImageModel != null;
                            listUrls.add(firebaseImageModel.getImagesUrl());

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}
