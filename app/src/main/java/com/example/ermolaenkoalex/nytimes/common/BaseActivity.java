package com.example.ermolaenkoalex.nytimes.common;

import butterknife.ButterKnife;

public abstract class BaseActivity extends MvpAppCompatActivity {
    @Override
    public void onContentChanged() {
        super.onContentChanged();

        ButterKnife.bind(this);
    }
}
