package com.covalsys.ttss_barcode.ui.scan.asset;

import static android.Manifest.permission.CAMERA;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomDataManager;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.database.entities.AssetCountScanLine;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.utils.DateUtils;
import com.covalsys.ttss_inv.data.network.models.get.GetDocumentHeaderModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class AssetCountBatchScanActivity extends AppCompatActivity implements AssetCountBatchScanAdapter.Callback {

    private CodeScanner mCodeScanner;
    AssetCountBatchScanAdapter adapter;
    EditText etN;

    private AppDatabase mDatabase;
    ApiService apiService;
    private RoomHelper helper;
    public MediatorLiveData<List<AssetCountScanLine>> scanModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_item_scan_activity_2);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        ImageView imView = findViewById(R.id.okText);
        imView.setVisibility(View.GONE);
        mCodeScanner = new CodeScanner(this, scannerView);
        mDatabase = AppDatabase.getDatabaseInstance(this);
        helper = new RoomDataManager(mDatabase);
        scanModel = new MediatorLiveData<>();
        initCameraPermission();

        RecyclerView view = (RecyclerView) findViewById(R.id.recyclerView);
        etN = (EditText)findViewById(R.id.inputText);
        etN.requestFocus();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(linearLayoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        adapter = new AssetCountBatchScanAdapter(new ArrayList<>(), AssetCountBatchScanActivity.this);
        adapter.setOnClick(this);
        view.setAdapter(adapter);

        imView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG_STR", "132" );
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
                        if(result.getBarcodeFormat().equals(BarcodeFormat.CODE_128)) {
                            if (!String.valueOf(result.getText()).isEmpty()) {
                                String text = String.valueOf(result.getText());
                                Log.e("TAG_ScanningCode", text);
                                if (mDatabase.assetCountScanLineDao().isAddToCart(text) != 1) {
                                    mDatabase.assetCountScanLineDao().add(new AssetCountScanLine(0, text, DateUtils.currentDateTimeYYYY()));
                                } else {
                                    Toast.makeText(AssetCountBatchScanActivity.this, "Scanned ItemCode Already Exist.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AssetCountBatchScanActivity.this, "Scanned data Empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        etN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etN.getText().hashCode() == s.hashCode()) {
                    try {
                        if (!String.valueOf(s).isEmpty()) {
                            String text = String.valueOf(s);
                            Log.e("TAG_ScanningCode", text);
                            if (mDatabase.assetCountScanLineDao().isAddToCart(text) != 1) {
                                mDatabase.assetCountScanLineDao().add(new AssetCountScanLine(0, text, DateUtils.currentDateTimeYYYY()));
                            } else {
                                Toast.makeText(AssetCountBatchScanActivity.this, "Scanned ItemCode. Already Exist.", Toast.LENGTH_SHORT).show();
                            }
                            etN.setText("");
                        } else {
                            //Toast.makeText(StockCountBatchScanActivity.this, "Scanned data Empty", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Log.e("TAG", e.getMessage());
                        etN.setText("");
                    }
                }
            }
        });

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder scanNOtInListData = new StringBuilder();
                StringBuilder scanSameAdded = new StringBuilder();
                StringBuilder alertText = new StringBuilder();
                List<AssetCountScanLine> line = mDatabase.assetCountScanLineDao().getDataList();
                for(AssetCountScanLine li:line){
                    if(mDatabase.assetCountLineDao().isAddToCart(li.getItemcode()) == 1){
                        if(mDatabase.assetCountLineDao().getLineStatus(li.getItemcode()).equalsIgnoreCase("A")){
                            if(scanSameAdded.length() == 0){
                                scanSameAdded.append(li.getItemcode());
                            }else {
                                scanSameAdded.append(", ").append(li.getItemcode());
                            }
                        }
                        mDatabase.assetCountLineDao().updateStatus(li.getItemcode(), li.getScanDate());
                    }else{
                        if(scanNOtInListData.length() == 0){
                            scanNOtInListData.append(li.getItemcode());
                        }else {
                            scanNOtInListData.append(", ").append(li.getItemcode());
                        }
                        Log.e("TAG_Scanning", li.getItemcode());
                    }
                }

                if(scanNOtInListData.length() != 0) {
                    alertText.append("Following scanned items are not exist in this Location, can you remove and add. "+scanNOtInListData);
                    alertText.append("\n");
                }

                if(scanSameAdded.length() != 0) {
                    alertText.append("Following items already scanned can you remove and add. "+scanSameAdded);
                    alertText.append("\n");
                }

                if(alertText.length() != 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AssetCountBatchScanActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage(alertText);
                    builder.setNegativeButton("Okay", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    /*builder.setPositiveButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    });*/
                    builder.show();

                    return;
                }

                Bundle args = new Bundle();
                args.putString("data", "12345");
                setResult(124, new Intent().putExtra("ScannedData", args));
                finish();
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        LiveData<List<AssetCountScanLine>> listLiveData = mDatabase.assetCountScanLineDao().getData();
        scanModel.addSource(listLiveData, pallets -> {
            scanModel.setValue(pallets);
        });

        scanModel.observe(this, model -> {
            adapter.clearList();
            adapter.addData(model);
            adapter.notifyDataSetChanged();
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
        Dexter.withContext(AssetCountBatchScanActivity.this)
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

    @Override
    public void onDelete(int position, AssetCountScanLine models) {
        mDatabase.assetCountScanLineDao().deleteData(models.getSlno());
        etN.requestFocus();
    }

    private void doAlertView(GetDocumentHeaderModel.ResponseObject model) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AssetCountBatchScanActivity.this);
        builder.setTitle("Alert");
        builder.setMessage("Scanned batch number against document exist are you sure want load. ");
        builder.setNegativeButton("Load", (dialog, which) -> {

        });
        builder.setPositiveButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
}