package com.example.ermolaenkoalex.nytimes.ui.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    private static final String URL_VK = "https://www.vk.com/ermolaenkoalex";
    private static final String URL_FB = "https://www.facebook.com/alexandr.ermolaenko";
    private static final String URL_TWITTER = "https://twitter.com/realDonaldTrump";

    @BindView(R.id.et_message)
    EditText message;

    public static void start(Activity activity) {
        Intent startIntent = new Intent(activity, AboutActivity.class);
        activity.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @OnClick(R.id.btn_next)
    void onButtonNext() {
        String messageText = message.getText().toString();
        if (messageText.isEmpty()) {
            Toast.makeText(this, R.string.warning_string_length, Toast.LENGTH_LONG).show();
        } else {
            SendMailActivity.start(this, messageText);
        }
    }

    @OnClick(R.id.icon_vk)
    void openVK() {
        openLink(URL_VK);
    }

    @OnClick(R.id.icon_fb)
    void openTelegram() {
        openLink(URL_FB);
    }

    @OnClick(R.id.icon_twitter)
    void openTwitter() {
        openLink(URL_TWITTER);
    }

    private void openLink(final String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, R.string.warning_no_browser_app, Toast.LENGTH_LONG).show();
            return;
        }

        startActivity(intent);
    }
}
