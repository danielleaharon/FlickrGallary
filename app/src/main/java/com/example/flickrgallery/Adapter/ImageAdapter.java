package com.example.flickrgallery.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flickrgallery.R;
import com.example.flickrgallery.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private final LayoutInflater inflater;
    Activity activity;

    private onItemClickListenr listener;
    List<Image> imageList;

    interface onItemClickListenr {
        void onClick(Image photo);
    }

    public ImageAdapter(Activity activity) {
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
        imageList = new ArrayList<>();
    }


    public void setList(List<Image> imageList2) {

        imageList = imageList2;
        notifyDataSetChanged();

    }

    void setonItemClickListenr(onItemClickListenr listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.photo_item, parent, false);


        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.bind(imageList.get(position));
    }

    @Override
    public int getItemCount() {


        return imageList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        onItemClickListenr listenr;
        CardView cardView;
        ImageView imageView;


        public ViewHolder(final View view, final onItemClickListenr listenr) {
            super(view);
            this.view = view;
            this.listenr = listenr;

            this.imageView = view.findViewById(R.id.imageView);
            this.cardView = view.findViewById(R.id.cardView);

            //Add a listener oo click on an image
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClick(imageList.get(position));
                        }
                    }


                }
            });


        }


        public void bind(Image image) {

            Glide.with(activity)
                    .load(image.getUrl()).placeholder(R.drawable.pic1)
                    .into(imageView);


        }

    }
}