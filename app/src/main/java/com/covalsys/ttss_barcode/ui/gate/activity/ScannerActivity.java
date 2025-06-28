package com.covalsys.ttss_barcode.ui.gate.activity;

import static com.covalsys.ttss_barcode.ui.gate.GateActivity.docNoBarcodeData;
import static com.covalsys.ttss_barcode.ui.gate.GateListActivity.isLoadHeader;
import static com.covalsys.ttss_barcode.ui.gate.GateListActivity.showBottomSheet;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.covalsys.ttss_barcode.R;
import com.google.android.material.textfield.TextInputEditText;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private TextView tvHeaderClose, tvScanner, tvManual, tvHeaderTitle;
    private TextInputEditText etBarcode;
    private LinearLayout llScanner, llManual;
    private Context context;
    private String tempBarcodeTxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_scanner);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initControls();

        mCodeScanner.setDecodeCallback(result -> {
            runOnUiThread(() -> {
                docNoBarcodeData = result.getText().trim();
                mCodeScanner.stopPreview();
                finish();
            });
        });

        scannerView.setOnClickListener(view -> {
            mCodeScanner.startPreview();
        });
    }

    private void initControls() {
        context = this;
        tvHeaderClose = findViewById(R.id.activity_scanner_tv_header_close);
        tvHeaderTitle = findViewById(R.id.activity_scanner_tv_header);
        tvScanner = findViewById(R.id.activity_scanner_tv_scanner);
        tvManual = findViewById(R.id.activity_scanner_tv_manual);
        llScanner = findViewById(R.id.activity_scanner_ll_scanner_layout);
        llManual = findViewById(R.id.activity_scanner_ll_manual_layout);
        etBarcode = findViewById(R.id.activity_scanner_et_barcode);
        scannerView = findViewById(R.id.activity_scanner_scanner_view);
        mCodeScanner = new CodeScanner(context, scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setTouchFocusEnabled(true);
        mCodeScanner.setAutoFocusInterval(2000);
        mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);

        llScanner.setVisibility(View.GONE);
        llManual.setVisibility(View.VISIBLE);

        closeKeyboard(etBarcode);
        setTextViewDrawableColor(tvScanner, R.color.disabled_color);
        setTextViewDrawableColor(tvManual, R.color.app_color);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        etBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tempBarcodeTxt = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etBarcode.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                docNoBarcodeData = tempBarcodeTxt.trim();
                if (!tempBarcodeTxt.isEmpty()) {
                    finish();
                }
            }
            return false;
        });

        tvHeaderClose.setOnClickListener(view -> {
            closeKeyboard(etBarcode);
            onBackPressed();
        });

        tvScanner.setOnClickListener(view -> {
            llScanner.setVisibility(View.VISIBLE);
            llManual.setVisibility(View.GONE);
            closeKeyboard(etBarcode);
            setTextViewDrawableColor(tvScanner, R.color.app_color);
            setTextViewDrawableColor(tvManual, R.color.disabled_color);
            mCodeScanner.startPreview();
        });

        tvManual.setOnClickListener(view -> {
            llScanner.setVisibility(View.GONE);
            llManual.setVisibility(View.VISIBLE);
            setTextViewDrawableColor(tvScanner, R.color.disabled_color);
            setTextViewDrawableColor(tvManual, R.color.app_color);
            mCodeScanner.stopPreview();
        });
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
        textView.setTextColor(ContextCompat.getColor(context, color));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        try {
            showBottomSheet = false;
            isLoadHeader = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }
}