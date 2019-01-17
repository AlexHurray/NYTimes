package com.example.ermolaenkoalex.nytimes.ui.about;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

public interface AboutView extends MvpView {
    @StateStrategyType(value = SingleStateStrategy.class)
    void setupPhoto(@IdRes int photoId);

    @StateStrategyType(value = SkipStrategy.class)
    void openEmailClient(@NonNull String email, @NonNull String message);

    @StateStrategyType(value = SkipStrategy.class)
    void openLink(@NonNull String url);
}
