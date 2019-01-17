package com.example.ermolaenkoalex.nytimes.ui.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ermolaenkoalex.nytimes.R;

import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {
    private static final String KEY_ID = "KEY_ID";

    public static SplashFragment newInstance(int fragmentId) {
        SplashFragment splashFragment = new SplashFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, fragmentId);
        splashFragment.setArguments(bundle);

        return splashFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        int id = getArguments().getInt(KEY_ID);
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        ImageView imageView = view.findViewById(R.id.iv_image);
        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .centerInside();
        Glide.with(this)
                .applyDefaultRequestOptions(imageOption)
                .load(id)
                .into(imageView);

        return view;
    }
}
