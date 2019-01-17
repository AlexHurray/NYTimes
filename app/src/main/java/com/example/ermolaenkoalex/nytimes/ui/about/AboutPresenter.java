package com.example.ermolaenkoalex.nytimes.ui.about;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import com.example.ermolaenkoalex.nytimes.R;

import androidx.annotation.NonNull;

@InjectViewState
public class AboutPresenter extends MvpPresenter<AboutView> {
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setupPhoto(R.drawable.profile);
    }

    public void onClickSendMessage(@NonNull final String email, @NonNull final String message) {
        getViewState().openEmailClient(email, message);
    }

    public void onOpenLink(@NonNull final String url) {
        getViewState().openLink(url);
    }
}
