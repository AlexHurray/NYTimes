package com.example.ermolaenkoalex.nytimes.ui.splash;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseFragmentActivity;
import com.example.ermolaenkoalex.nytimes.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.relex.circleindicator.CircleIndicator;

public class SplashActivity extends BaseFragmentActivity {

    private static int[] SCREENS = new int[]{R.drawable.intro1, R.drawable.intro2, R.drawable.intro3};
    private static final String KEY_LOGO = "KEY_LOGO";
    private static final long TIME_DELAY = 10;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.pager_splash)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean showLogo = sharedPreferences.getBoolean(KEY_LOGO, true);
        sharedPreferences.edit().putBoolean(KEY_LOGO, !showLogo).apply();

        if (showLogo) {
            setContentView(R.layout.activity_splash);

            PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

            CircleIndicator indicator = findViewById(R.id.indicator);
            indicator.setViewPager(pager);

            Disposable disposable = Completable.complete()
                    .delay(TIME_DELAY, TimeUnit.SECONDS)
                    .subscribe(this::startSecondActivity);
            compositeDisposable.add(disposable);
        } else {
            startSecondActivity();
        }
    }

    private void startSecondActivity() {
        MainActivity.start(this);
        finish();
    }

    @OnClick(R.id.btn_skip)
    void onButtonSkip() {
        compositeDisposable.dispose();
        startSecondActivity();
    }

    @Override
    protected void onStop() {
        compositeDisposable.dispose();
        super.onStop();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SplashFragment.newInstance(SCREENS[position]);
        }

        @Override
        public int getCount() {
            return SCREENS.length;
        }
    }
}
