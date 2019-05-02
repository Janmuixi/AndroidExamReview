package com.example.jan.listimagesexamandroid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
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
    ArrayList<FirebaseImageModel> listItems;
    MediaPlayer mediaPlayer;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_photo:
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                break;
            case R.id.action_pause_music:
                mediaPlayer.stop();
                break;
            default:
                break;
        }

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_images);
        listItems = new ArrayList<>();
        imagesList = findViewById(R.id.imagesListView);
        imagesList.setLayoutManager(new LinearLayoutManager(this));

        // Toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Images List");

        // MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.bensoundukulele);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        final AdapterImages adapter = new AdapterImages(listItems);

        imagesList.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listItems.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FirebaseImageModel firebaseImageModel = snapshot.getValue(FirebaseImageModel.class);
                            assert firebaseImageModel != null;
                            listItems.add(firebaseImageModel);

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

}
