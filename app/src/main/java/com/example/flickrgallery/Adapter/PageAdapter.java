package com.example.flickrgallery.Adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.flickrgallery.GalleryFragment;
import com.example.flickrgallery.GalleryFragmentDirections;
import com.example.flickrgallery.MainActivity;
import com.example.flickrgallery.R;
import com.example.flickrgallery.model.Image;
import com.example.flickrgallery.model.PagesModel;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {


    private final LayoutInflater inflater;
    Activity activity;
    GalleryFragment parent;

    public PageAdapter(Activity activity, GalleryFragment parent) {
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
        this.parent = (GalleryFragment) parent;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.page_item, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.bind(position + 1);
    }

    @Override
    public int getItemCount() {


        return PagesModel.instance.getPagesTotalNumber();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        RecyclerView pageRecycler;
        ImageAdapter imageAdapter;
        ConstraintLayout Layout;

        private StaggeredGridLayoutManager _sGridLayoutManager;


        public ViewHolder(final View view) {
            super(view);
            this.view = view;

            pageRecycler = view.findViewById(R.id.pageRecycler);
            pageRecycler.setHasFixedSize(true);
            Layout = view.findViewById(R.id.Layout);
            _sGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            imageAdapter = new ImageAdapter(activity);
            pageRecycler.setLayoutManager(_sGridLayoutManager);

//When moving to the next view - an update will be sent to the current page number
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    parent.UpdatePageNumber(getAdapterPosition() + 1);


                }

                @Override
                public void onViewDetachedFromWindow(View v) {

                }
            });


        }


        public void bind(int position) {

            //Loading a new page of images
            if (!PagesModel.instance.getImageMap().containsKey(position + 1) && (position + 1) <= getItemCount())
                PagesModel.instance.getImage(activity, position + 1, null);

            imageAdapter.setList(PagesModel.instance.getImageMap().get(position));
            pageRecycler.setAdapter(imageAdapter);

            //When an image is clicked, it will move to full screen - ImageFragment , and send him the relevant parameters
            imageAdapter.setonItemClickListenr(new ImageAdapter.onItemClickListenr() {
                @Override
                public void onClick(Image photo) {

                    NavDirections directions = GalleryFragmentDirections.actionGalleryFragmentToImageFragment(photo.getTitle(), photo.getUrl());

                    Navigation.findNavController(view).navigate(directions);

                }
            });

        }


    }
}