package com.covalsys.ttss_barcode.ui.scan;

import static android.Manifest.permission.CAMERA;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.covalsys.ttss_barcode.R;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class CustomScanActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_scan_activity);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        ImageView imView = findViewById(R.id.okText);
        EditText etText = findViewById(R.id.inputText);
        mCodeScanner = new CodeScanner(this, scannerView);
        initCameraPermission();

        imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etText.getText().toString().isEmpty()){
                    Toast.makeText(CustomScanActivity.this, "Enter valid value.", Toast.LENGTH_SHORT).show();
                }else {
                    String xyz = etText.getText().toString();
                    setResult(123, new Intent().putExtra("ScannedData", xyz));
                    finish();
                }
            }
        });

        findViewById(R.id.scanClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.manual_la).setVisibility(View.GONE);
                findViewById(R.id.scanner_view).setVisibility(View.VISIBLE);
            }
        });
        
        findViewById(R.id.manualClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.manual_la).setVisibility(View.VISIBLE);
                findViewById(R.id.scanner_view).setVisibility(View.GONE);
            }
        });

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CustomScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        setResult(123, new Intent().putExtra("ScannedData", result.getText()));
                        finish();
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initCameraPermission() {
        Dexter.withContext(CustomScanActivity.this)
                .withPermissions(CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            //mImageInputHelper.takePhotoWithCamera();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}
