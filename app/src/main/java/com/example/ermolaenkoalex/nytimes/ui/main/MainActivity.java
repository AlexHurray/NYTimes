package com.example.ermolaenkoalex.nytimes.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.common.BaseFragment;
import com.example.ermolaenkoalex.nytimes.ui.about.AboutActivity;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.NewsListFragment;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;
import com.example.ermolaenkoalex.nytimes.ui.main.newsdetails.NewsDetailsFragment;
import com.example.ermolaenkoalex.nytimes.ui.main.newsedit.NewsEditFragment;
import com.example.ermolaenkoalex.nytimes.ui.preferences.PreferencesActivity;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements NewsListFragment.NewsListFragmentListener,
        NewsDetailsFragment.NewsDetailsFragmentListener,
        BaseFragment.BaseFragmentListener {
    private static final String BACKSTACK_INIT_NAME = "BACKSTACK_INIT_NAME";
    private static final String FRAGMENT_LIST_TAG = "FRAGMENT_LIST_TAG";
    private static final String FRAGMENT_DETAILS_TAG = "FRAGMENT_DETAILS_TAG";
    private static final String FRAGMENT_EDIT_TAG = "FRAGMENT_EDIT_TAG";

    @BindView(R.id.ll_sections)
    @NonNull
    LinearLayout llSections;

    private boolean isTwoPanel;

    public static void start(Activity activity) {
        Intent startIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTwoPanel = findViewById(R.id.frame_detail) != null;

        addChips();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_list, new NewsListFragment(), FRAGMENT_LIST_TAG)
                    .addToBackStack(BACKSTACK_INIT_NAME)
                    .commit();
        } else {
            Fragment details = getSupportFragmentManager().findFragmentByTag(FRAGMENT_DETAILS_TAG);
            Fragment edit = getSupportFragmentManager().findFragmentByTag(FRAGMENT_EDIT_TAG);
            int frameId = isTwoPanel ? R.id.frame_detail : R.id.frame_list;

            if (details != null) {
                getSupportFragmentManager().popBackStackImmediate(BACKSTACK_INIT_NAME, 0);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(frameId, details, FRAGMENT_DETAILS_TAG)
                        .addToBackStack(null)
                        .commit();

                if (edit != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(frameId, edit, FRAGMENT_EDIT_TAG)
                            .addToBackStack(null)
                            .commit();
                }
            }
        }
    }

    @Override
    public void onNewsClicked(int id) {
        NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.newInstance(id);
        int frameId = isTwoPanel ? R.id.frame_detail : R.id.frame_list;

        getSupportFragmentManager().popBackStackImmediate(BACKSTACK_INIT_NAME, 0);

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(frameId, newsDetailsFragment, FRAGMENT_DETAILS_TAG)
                .addToBackStack(null)
                .commit();
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
        NewsEditFragment newsEditFragment = NewsEditFragment.newInstance(id);
        int frameId = isTwoPanel ? R.id.frame_detail : R.id.frame_list;

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(frameId, newsEditFragment, FRAGMENT_EDIT_TAG)
                .addToBackStack(null)
                .commit();
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
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        onBackPressed();
        return true;
    }

    private void addChips() {
        for (Section section : Section.values()) {
            Chip chip = new Chip(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            TextViewCompat.setTextAppearance(chip, R.style.TextAppearance_AppCompat_Title_Inverse);

            int spacing = getResources().getDimensionPixelSize(R.dimen.spacing_standard);
            params.setMargins(spacing, spacing, spacing, spacing);
            params.setMarginStart(spacing);
            params.setMarginEnd(spacing);

            chip.setLayoutParams(params);
            chip.setChipBackgroundColorResource(R.color.colorPrimaryDark);
            chip.setText(section.getSectionNameResId());
            chip.setOnClickListener(view -> clickOnChip(section));

            llSections.addView(chip);
        }
    }

    private void clickOnChip(Section section) {
        getSupportFragmentManager().popBackStackImmediate(BACKSTACK_INIT_NAME, 0);

        NewsListFragment listFragment = (NewsListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_TAG);

        if (listFragment != null) {
            listFragment.loadNews(section);
        }
    }
}
