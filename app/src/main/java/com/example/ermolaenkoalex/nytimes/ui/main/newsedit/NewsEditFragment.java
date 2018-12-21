package com.example.ermolaenkoalex.nytimes.ui.main.newsedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseFragment;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class NewsEditFragment extends BaseFragment implements NewsEditView {
    private static final String KEY_ID = "KEY_ID";

    @BindView(R.id.et_title)
    EditText etTitle;

    @BindView(R.id.et_preview_text)
    EditText etPreviewText;

    @BindView(R.id.et_news_url)
    EditText etNewsUrl;

    @BindView(R.id.et_image_url)
    EditText etImageUrl;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @InjectPresenter
    NewsEditPresenter presenter;

    public static NewsEditFragment newInstance(int id) {
        NewsEditFragment newsEditFragment = new NewsEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        newsEditFragment.setArguments(bundle);

        return newsEditFragment;
    }

    @ProvidePresenter
    NewsEditPresenter providePresenter() {
        int id = getArguments().getInt(KEY_ID, 0);

        return new NewsEditPresenter(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_news_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(getString(R.string.title_edit_news), true);
    }

    @Override
    public void setData(NewsItem data) {
        etTitle.setText(data.getTitle());
        etPreviewText.setText(data.getPreviewText());
        etNewsUrl.setText(data.getItemUrl());
        etImageUrl.setText(data.getImageUrl());
    }

    @Override
    public void close() {
        goBack();
    }

    @Override
    public void showErrorMessage(@IdRes int errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_apply:
                presenter.saveData(etTitle.getText().toString(),
                        etPreviewText.getText().toString(),
                        etImageUrl.getText().toString(),
                        etNewsUrl.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
