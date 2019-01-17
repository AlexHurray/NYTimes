package com.example.ermolaenkoalex.nytimes.ui.main.newsdetails;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ermolaenkoalex.nytimes.MyApp;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseFragment;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.utils.SharedPreferencesHelper;
import com.example.ermolaenkoalex.nytimes.utils.StringUtils;

import javax.inject.Inject;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import toothpick.Toothpick;

public class NewsDetailsFragment extends BaseFragment implements NewsDetailsView {
    private static final String KEY_ID = "KEY_ID";

    @BindView(R.id.iv_image)
    ImageView imageView;

    @BindView(R.id.tv_title)
    TextView titleView;

    @BindView(R.id.tv_date)
    TextView dateView;

    @BindView(R.id.tv_full_text)
    TextView fullTextView;

    @BindView(R.id.tv_link)
    TextView linkTextView;

    private int id;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @NonNull
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @InjectPresenter
    NewsDetailsPresenter presenter;

    @NonNull
    private NewsDetailsFragmentListener listener;

    public static NewsDetailsFragment newInstance(int id) {
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        newsDetailsFragment.setArguments(bundle);

        return newsDetailsFragment;
    }

    @ProvidePresenter
    NewsDetailsPresenter providePresenter() {
        id = getArguments().getInt(KEY_ID, 0);

        return new NewsDetailsPresenter(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof NewsDetailsFragmentListener) {
            listener = (NewsDetailsFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, MyApp.getAppScope());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    @Override
    public void setData(NewsItem newsItem) {
        setTitle(newsItem.getCategory().toUpperCase(), true);

        id = newsItem.getId();
        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .centerCrop();
        Glide.with(this)
                .applyDefaultRequestOptions(imageOption)
                .load(newsItem.getImageUrl())
                .into(imageView);
        titleView.setText(newsItem.getTitle());
        dateView.setText(StringUtils.formatDate(getContext(), newsItem.getPublishDate()));
        fullTextView.setText(newsItem.getPreviewText());
        linkTextView.setText(Html.fromHtml(
                "<a href=\"" + newsItem.getItemUrl() + "\">" + getString(R.string.label_see_more) + "</a> "));
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
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
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!sharedPreferencesHelper.isEditEnabled()) {
            menu.findItem(R.id.action_edit).setVisible(false);
        }
        if (!sharedPreferencesHelper.isDeleteEnabled()) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                if (listener != null) {
                    listener.onNewsEdit(id);
                }
                return true;
            case R.id.action_delete:
                presenter.delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public interface NewsDetailsFragmentListener {
        void onNewsEdit(int id);
    }
}
