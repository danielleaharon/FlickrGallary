package com.example.flickrgallery;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flickrgallery.Adapter.PageAdapter;
import com.example.flickrgallery.model.PagesModel;


public class GalleryFragment extends Fragment {


    TextView pageText;
    RecyclerView image_recycler;
    PageAdapter pageAdapter;
    TextView total_num;
    View view;
    MainActivity mainActivity;

    public GalleryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //get parent Activity
        this.mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initializeLayout();
        return view;

    }

    public void initializeLayout() {
        pageText = view.findViewById(R.id.page);
        total_num = view.findViewById(R.id.total_num);

        image_recycler = view.findViewById(R.id.image_recycler);
        image_recycler.setHasFixedSize(true);
        LinearLayoutManager layoutManger = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        image_recycler.setLayoutManager(layoutManger);

        if (!PagesModel.instance.getImageMap().containsKey(1)) {
            //Loading the first page of images
            PagesModel.instance.getImage(mainActivity, 1, new ImageApi.onGetImageListener() {
                @Override
                public void onCompleted(boolean completed) {
                    if (completed) {
                        total_num.setText(String.valueOf(PagesModel.instance.getTotalImage()));
                        pageAdapter = new PageAdapter(mainActivity, GalleryFragment.this);
                        image_recycler.setAdapter(pageAdapter);
                    }


                }
            });
        } else {
            total_num.setText(String.valueOf(PagesModel.instance.getTotalImage()));
            pageAdapter = new PageAdapter(mainActivity, GalleryFragment.this);
            image_recycler.setAdapter(pageAdapter);
        }


    }

    //Update current page number
    public void UpdatePageNumber(int pageNumber) {

        String text = pageNumber + " - " + PagesModel.instance.getPagesTotalNumber();
        pageText.setText(text);
    }
}