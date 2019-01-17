package com.example.ermolaenkoalex.nytimes.common;


import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends AppCompatActivity {
    @Override
    public void onContentChanged() {
        super.onContentChanged();

        ButterKnife.bind(this);
    }
}
