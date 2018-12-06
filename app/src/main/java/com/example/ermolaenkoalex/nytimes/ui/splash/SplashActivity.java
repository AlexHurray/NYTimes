package com.example.ermolaenkoalex.nytimes.ui.splash;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.ui.newslist.NewsListActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity {
    private static final String KEY_LOGO = "KEY_LOGO";
    private static final long TIME_DELAY = 3;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean showLogo = sharedPreferences.getBoolean(KEY_LOGO, true);
        sharedPreferences.edit().putBoolean(KEY_LOGO, !showLogo).apply();

        if (showLogo) {
            setContentView(R.layout.activity_splash);
            Disposable disposable = Completable.complete()
                    .delay(TIME_DELAY, TimeUnit.SECONDS)
                    .subscribe(this::startSecondActivity);
            compositeDisposable.add(disposable);
        } else {
            startSecondActivity();
        }
    }

    private void startSecondActivity() {
        NewsListActivity.start(this);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

}
