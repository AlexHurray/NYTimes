package com.example.ermolaenkoalex.nytimes.common;


import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends FragmentActivity {
    @Override
    public void onContentChanged() {
        super.onContentChanged();

        ButterKnife.bind(this);
    }
}
