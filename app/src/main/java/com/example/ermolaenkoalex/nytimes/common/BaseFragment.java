package com.example.ermolaenkoalex.nytimes.common;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private String title;
    private boolean displayHome;

    @Nullable
    private BaseFragmentListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseFragmentListener) {
            listener = (BaseFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public void showTitle() {
        if (listener != null) {
            listener.setTitle(title, displayHome);
        }
    }

    public void goBack() {
        if (listener != null) {
            listener.goBack();
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
