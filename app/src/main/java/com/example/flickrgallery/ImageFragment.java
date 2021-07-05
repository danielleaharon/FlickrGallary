package com.example.flickrgallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class ImageFragment extends Fragment {


    ImageView image;
    TextView title_txt;
    ImageButton cancel;

    public ImageFragment() {
    }


    public static ImageFragment newInstance(String param1, String param2) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        image = view.findViewById(R.id.image);
        title_txt = view.findViewById(R.id.title);
        cancel = view.findViewById(R.id.cancel);

        //Receive the parameters sent from the previous page
        String url = ImageFragmentArgs.fromBundle(getArguments()).getUrl();
        String title = ImageFragmentArgs.fromBundle(getArguments()).getTitle();

        //set data
        Glide.with(getActivity())
                .load(url).placeholder(R.drawable.pic1)
                .into(image);
        title_txt.setText(title);

        //Back to the gallery
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();

            }
        });
        return view;
    }
}