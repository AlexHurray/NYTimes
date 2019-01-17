package com.example.ermolaenkoalex.nytimes.ui.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity implements AboutView {

    private static final String URL_VK = "https://www.vk.com/ermolaenkoalex";
    private static final String URL_FB = "https://www.facebook.com/alexandr.ermolaenko";
    private static final String URL_TWITTER = "https://twitter.com/realDonaldTrump";
    private static final String FIELD_SUBJECT = "Hello, Alex!";
    private static final String FIELD_TO = "ermolaenkoalex@gmail.com";

    @BindView(R.id.et_message)
    EditText message;

    @BindView(R.id.iv_image)
    ImageView imageView;

    @InjectPresenter
    AboutPresenter presenter;

    public static void start(Activity activity) {
        Intent startIntent = new Intent(activity, AboutActivity.class);
        activity.startActivity(startIntent);
    }

    @ProvidePresenter
    AboutPresenter providePresenter() {
        return new AboutPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @OnClick(R.id.btn_next)
    void onButtonNext() {
        presenter.onClickSendMessage(FIELD_TO, message.getText().toString());
    }

    @OnClick(R.id.icon_vk)
    void openVK() {
        presenter.onOpenLink(URL_VK);
    }

    @OnClick(R.id.icon_fb)
    void openFacebook() {
        presenter.onOpenLink(URL_FB);
    }

    @OnClick(R.id.icon_twitter)
    void openTwitter() {
        presenter.onOpenLink(URL_TWITTER);
    }

    @Override
    public void openLink(final String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, R.string.warning_no_browser_app, Toast.LENGTH_LONG).show();
            return;
        }

        startActivity(intent);
    }

    @Override
    public void setupPhoto(int photoId) {
        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .centerInside();
        Glide.with(this)
                .applyDefaultRequestOptions(imageOption)
                .load(photoId)
                .into(imageView);
    }

    @Override
    public void openEmailClient(@NonNull final String email, @NonNull final String message) {
        if (message.isEmpty()) {
            Toast.makeText(this, R.string.warning_string_length, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, FIELD_SUBJECT);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            if (intent.resolveActivity(getPackageManager()) == null) {
                Toast.makeText(this, R.string.warning_no_mail_app, Toast.LENGTH_LONG).show();
                return;
            }

            startActivity(intent);
        }
    }
}
