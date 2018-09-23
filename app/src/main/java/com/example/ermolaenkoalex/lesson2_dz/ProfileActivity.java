package com.example.ermolaenkoalex.lesson2_dz;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.et_message)
    EditText mMessage;

    @BindView(R.id.mainView)
    LinearLayout mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        TextView copyright = new TextView(this);
        copyright.setText(R.string.copyright);
        applyLayoutMargins4TextView(copyright);

        mMainView.addView(copyright);
    }

    @OnClick(R.id.btn_next)
    void onButtonNext(){
        String message = mMessage.getText().toString();
        if (message.length() > 0){
            SecondActivity.start(this, message);
        } else{
            Toast.makeText(this, R.string.warning_string_length, Toast.LENGTH_LONG).show();
        }
    }

    private void applyLayoutMargins4TextView(@NonNull TextView textview){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TypedArray ta = obtainStyledAttributes(R.style.standard_text_style, R.styleable.standard_view_style_table);
        int bottom = ta.getDimensionPixelSize(R.styleable.standard_view_style_table_android_layout_marginBottom, 0);
        int top = ta.getDimensionPixelSize(R.styleable.standard_view_style_table_android_layout_marginTop, 0);
        int start = ta.getDimensionPixelSize(R.styleable.standard_view_style_table_android_layout_marginStart, 0);
        int end = ta.getDimensionPixelSize(R.styleable.standard_view_style_table_android_layout_marginEnd, 0);

        params.setMargins(0, top, 0, bottom);
        params.setMarginStart(start);
        params.setMarginEnd(end);

        if (Build.VERSION.SDK_INT < 23) {
            textview.setTextAppearance(this, R.style.standard_text_style);
        } else {
            textview.setTextAppearance(R.style.standard_text_style);
        }

        ta.recycle();

        textview.setLayoutParams(params);
    }
}
