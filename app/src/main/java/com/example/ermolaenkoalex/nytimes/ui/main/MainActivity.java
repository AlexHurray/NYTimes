package com.example.ermolaenkoalex.nytimes.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseFragment;
import com.example.ermolaenkoalex.nytimes.common.BaseOneTwoFragmentActivity;
import com.example.ermolaenkoalex.nytimes.ui.about.AboutActivity;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.NewsListFragment;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;
import com.example.ermolaenkoalex.nytimes.ui.main.newsdetails.NewsDetailsFragment;
import com.example.ermolaenkoalex.nytimes.ui.main.newsedit.NewsEditFragment;
import com.example.ermolaenkoalex.nytimes.ui.preferences.PreferencesActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MainActivity extends BaseOneTwoFragmentActivity implements NewsListFragment.NewsListFragmentListener,
        NewsDetailsFragment.NewsDetailsFragmentListener,
        BaseFragment.BaseFragmentListener {
    private static final String FRAGMENT_LIST_TAG = "FRAGMENT_LIST_TAG";
    private static final String FRAGMENT_DETAILS_TAG = "FRAGMENT_DETAILS_TAG";
    private static final String FRAGMENT_EDIT_TAG = "FRAGMENT_EDIT_TAG";

    @BindView(R.id.recycler_view_chips)
    @NonNull
    RecyclerView recyclerViewChips;

    @BindView(R.id.frame_detail)
    @NonNull
    FrameLayout frameDetails;

    public static void start(Activity activity) {
        Intent startIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addChips();

        if (savedInstanceState == null) {
            changePrimaryFragment(new NewsListFragment(), FRAGMENT_LIST_TAG);
        } else {
            Fragment details = getSupportFragmentManager().findFragmentByTag(FRAGMENT_DETAILS_TAG);

            if (details != null) {
                frameDetails.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNewsClicked(int id) {
        changeSecondaryFragment(NewsDetailsFragment.newInstance(id), FRAGMENT_DETAILS_TAG, true);
        frameDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAboutClicked() {
        AboutActivity.start(this);
    }

    @Override
    public void onPreferencesClicked() {
        PreferencesActivity.start(this);
    }

    @Override
    public void onNewsEdit(int id) {
        changeSecondaryFragment(NewsEditFragment.newInstance(id), FRAGMENT_EDIT_TAG);
    }

    @Override
    public void setTitle(String title, boolean displayHome) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
            ab.setDisplayHomeAsUpEnabled(displayHome);
        }
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();

            NewsDetailsFragment detailsFragment = (NewsDetailsFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_DETAILS_TAG);
            NewsListFragment listFragment = (NewsListFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_LIST_TAG);

            if (detailsFragment != null && backStackEntryCount > 2) {
                detailsFragment.showTitle();
            } else if (listFragment != null) {
                listFragment.showTitle();
                frameDetails.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addChips() {
        recyclerViewChips.setAdapter(new ChipsRecyclerAdapter(this, Section.values(), this::clickOnChip));
        recyclerViewChips.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void clickOnChip(Section section) {
        resetSecondaryFragment();
        frameDetails.setVisibility(View.GONE);

        NewsListFragment listFragment = (NewsListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_TAG);

        if (listFragment != null) {
            listFragment.loadNews(section);
            listFragment.showTitle();
        }
    }
}
