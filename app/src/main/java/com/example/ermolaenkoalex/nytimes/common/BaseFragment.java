package com.example.ermolaenkoalex.nytimes.common;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public abstract class BaseFragment extends MvpAppCompatFragment {

    private String title;
    private boolean displayHome;

    @Nullable
    private BaseFragmentListener baseFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseFragmentListener) {
            baseFragmentListener = (BaseFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        baseFragmentListener = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public void showTitle() {
        if (baseFragmentListener != null) {
            baseFragmentListener.setTitle(title, displayHome);
        }
    }

    protected void goBack() {
        if (baseFragmentListener != null) {
            baseFragmentListener.goBack();
        }
    }

    protected void setTitle(String title, boolean displayHome) {
        this.title = title;
        this.displayHome = displayHome;
        showTitle();
    }

    public interface BaseFragmentListener {
        void setTitle(String title, boolean displayHome);

        void goBack();
    }
}
