package com.example.ermolaenkoalex.lesson2_dz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {

    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    private static final String FIELD_TO = "ermolaenkoalex@gmail.com";
    private static final String FIELD_SUBJECT = "Hello, Alex!";

    @BindView(R.id.tv_message)
    TextView label;

    public static void start(Activity activity, String text) {
        Intent startIntent = new Intent(activity, SecondActivity.class);
        startIntent.putExtra(KEY_MESSAGE, text);
        activity.startActivity(startIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);

        final String emailText = getIntent().getStringExtra(KEY_MESSAGE);
        label.setText(emailText);
    }

    @OnClick(R.id.btn_next)
    void sendEmail() {
        final String emailText = label.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{FIELD_TO});
        intent.putExtra(Intent.EXTRA_SUBJECT, FIELD_SUBJECT);
        intent.putExtra(Intent.EXTRA_TEXT, emailText);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, R.string.warning_no_mail_app, Toast.LENGTH_LONG).show();
            return;
        }

        startActivity(intent);
    }
}
