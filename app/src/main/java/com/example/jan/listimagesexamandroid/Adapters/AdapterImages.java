package com.example.jan.listimagesexamandroid.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jan.listimagesexamandroid.Models.FirebaseImageModel;
import com.example.jan.listimagesexamandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHoldersImages> {
    private ArrayList<FirebaseImageModel> listItems;

    public AdapterImages(ArrayList<FirebaseImageModel> listImages) {
        this.listItems = listImages;
    }
    @NonNull
    @Override
    public ViewHoldersImages onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_images, null, false);

        return new ViewHoldersImages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoldersImages viewHolderImages, int i) {
        viewHolderImages.assignValues(listItems.get(i));
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }



    class ViewHoldersImages extends RecyclerView.ViewHolder {
        View currentView;
        ImageView image;
        TextView description;
        TextView aula;
        CheckBox isResolved;
        ImageView cross;

        ViewHoldersImages(@NonNull View itemView) {
            super(itemView);
            currentView = itemView;
            image = itemView.findViewById(R.id.image);
            description = itemView.findViewById(R.id.description);
            aula = itemView.findViewById(R.id.aula);
            isResolved = itemView.findViewById(R.id.isResolved);
            cross = itemView.findViewById(R.id.cross);

            isResolved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isResolved.isChecked()) {
                        cross.animate().alpha(1);
                        cross.animate().translationX(-900).translationY(10);
                    } else {
                        cross.animate().alpha(0);
                    }
                }
            });
        }


        void assignValues(FirebaseImageModel firebaseImageModel) {
            Picasso.get().load(firebaseImageModel.getImagesUrl()).into(image);
            description.setText(firebaseImageModel.getDescription());
            aula.setText(firebaseImageModel.getAula());
        }
    }
}
