package com.example.ermolaenkoalex.NYTimes;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    private static final String URL_VK = "https://www.vk.com/ermolaenkoalex";
    private static final String URL_FB = "https://www.facebook.com/alexandr.ermolaenko";
    private static final String URL_TWITTER = "https://twitter.com/realDonaldTrump";

    @BindView(R.id.et_message)
    EditText message;

    @BindView(R.id.main_view)
    LinearLayout mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        TextView copyright = new TextView(this);
        copyright.setText(R.string.copyright);
        applyLayoutMargins4TextView(copyright);

        mainView.addView(copyright);
    }

    @OnClick(R.id.btn_next)
    void onButtonNext() {
        String messageText = message.getText().toString();
        if (messageText.isEmpty()) {
            Toast.makeText(this, R.string.warning_string_length, Toast.LENGTH_LONG).show();
        } else {
            SecondActivity.start(this, messageText);
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

    private void applyLayoutMargins4TextView(@NonNull TextView textview) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TypedArray ta = obtainStyledAttributes(R.style.StandardTextStyle, R.styleable.StandardViewStyleTable);
        int bottom = ta.getDimensionPixelSize(R.styleable.StandardViewStyleTable_android_layout_marginBottom, 0);
        int top = ta.getDimensionPixelSize(R.styleable.StandardViewStyleTable_android_layout_marginTop, 0);
        int start = ta.getDimensionPixelSize(R.styleable.StandardViewStyleTable_android_layout_marginStart, 0);
        int end = ta.getDimensionPixelSize(R.styleable.StandardViewStyleTable_android_layout_marginEnd, 0);

        params.setMargins(0, top, 0, bottom);
        params.setMarginStart(start);
        params.setMarginEnd(end);

        if (Build.VERSION.SDK_INT < 23) {
            textview.setTextAppearance(this, R.style.StandardTextStyle);
        } else {
            textview.setTextAppearance(R.style.StandardTextStyle);
        }

        ta.recycle();

        textview.setLayoutParams(params);
    }
}
