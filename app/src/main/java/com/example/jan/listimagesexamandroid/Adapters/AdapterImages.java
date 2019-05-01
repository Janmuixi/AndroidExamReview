package com.example.jan.listimagesexamandroid.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jan.listimagesexamandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHoldersImages> implements View.OnClickListener {
    private ArrayList<String> listImages;

    public AdapterImages(ArrayList<String> listImages) {
        this.listImages = listImages;
    }
    private View.OnClickListener listener;
    @NonNull
    @Override
    public ViewHoldersImages onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_images, null, false);

        view.setOnClickListener(this);

        return new ViewHoldersImages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoldersImages viewHolderImages, int i) {
        viewHolderImages.assignImage(listImages.get(i));
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return listImages.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    class ViewHoldersImages extends RecyclerView.ViewHolder {
        ImageView image;
        ViewHoldersImages(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }


        void assignImage(String s) {
            Picasso.get().load(s).into(image);
        }
    }
}
