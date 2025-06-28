package com.covalsys.ttss_barcode.ui.gate.activity;

import static com.covalsys.ttss_barcode.ui.gate.GateActivity.docNoBarcodeData;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.listBarcodeData;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strType;
import static com.covalsys.ttss_barcode.ui.gate.GateListActivity.isLoadHeader;
import static com.covalsys.ttss_barcode.ui.gate.GateListActivity.showBottomSheet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.ScanMode;
import com.covalsys.ttss_barcode.BR;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.network.models.BarcodeList.ResultItem;
import com.covalsys.ttss_barcode.data.network.models.HeaderModel.Result;
import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;
import com.covalsys.ttss_barcode.databinding.ActivityBatchScannerBinding;
import com.covalsys.ttss_barcode.ui.ViewModelProviderFactory;
import com.covalsys.ttss_barcode.ui.base.BaseActivity;
import com.covalsys.ttss_barcode.ui.gate.GateActivity;
import com.covalsys.ttss_barcode.ui.gate.GateNavigator;
import com.covalsys.ttss_barcode.ui.gate.GateViewModel;
import com.covalsys.ttss_barcode.ui.gate.adapter.BatchListRvAdapter;
import com.covalsys.ttss_barcode.ui.gate.adapter.StickHeaderItemDecoration;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class BatchScannerActivity extends BaseActivity<GateViewModel, ActivityBatchScannerBinding> implements GateNavigator {

    @Inject
    ViewModelProviderFactory factory;

    private GateViewModel mGateViewModel;

    public static List<String> batchList;
    private final String tempBarcodeTxt = "";
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private TextView tvHeaderClose, tvScanner, tvManual, tvHeaderTitle;
    private TextInputEditText etBarcode;
    private LinearLayout llScanner, llManual;
    private Context context;
    private RecyclerView recyclerView;
    private BatchListRvAdapter batchListRvAdapter;
    private MaterialButton btnAdd, btnGotoItem;
    private Intent intent;
    private Result result;
    private List<String> batList = new ArrayList<>();
    private List<ResultItem> itemArrayList = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.listViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_batch_scanner;
    }

    @Override
    protected GateViewModel getViewModel() {
        mGateViewModel = new ViewModelProvider(this, factory).get(GateViewModel.class);
        return mGateViewModel;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, BatchScannerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mGateViewModel.setNavigator(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        context = this;
        initControls();

        mCodeScanner.setDecodeCallback(result -> {
            runOnUiThread(() -> {
                String barcode = result.getText().trim();
                if (!barcode.isEmpty()) {
                    if (!batchList.contains(barcode)) {
                        batchList.add(barcode);
                        checkList(barcode);
                    }
                    batchListRvAdapter.setData(this, batchList);
                    etBarcode.setText("");
                    recyclerView.setVisibility(View.VISIBLE);
                    btnGotoItem.setBackgroundColor(getResources().getColor(R.color.app_color));
                    btnGotoItem.setTextColor(getResources().getColor(R.color.white));
                    btnGotoItem.setEnabled(true);
                }
            });
        });

        scannerView.setOnClickListener(view -> {
            mCodeScanner.startPreview();
        });

        mGateViewModel.getHeaderPostDataLiveData().observe(this, postHeaderModel -> {
            if (postHeaderModel != null) {
                if (postHeaderModel.data.isIsSuccess()) {
                    itemArrayList.remove(0);
                    mGateViewModel.sendBarCodeList(itemArrayList, ((Double) postHeaderModel.data.getResult()).intValue());
                }
            }
        });

    }

    private void initControls() {

        mGateViewModel = new ViewModelProvider(this).get(GateViewModel.class);
        tvHeaderClose = findViewById(R.id.activity_batch_scanner_tv_header_close);
        tvHeaderTitle = findViewById(R.id.activity_batch_scanner_tv_header);
        tvScanner = findViewById(R.id.activity_batch_scanner_tv_scanner);
        tvManual = findViewById(R.id.activity_batch_scanner_tv_manual);
        llScanner = findViewById(R.id.activity_batch_scanner_ll_scanner_layout);
        llManual = findViewById(R.id.activity_batch_scanner_ll_manual_layout);
        etBarcode = findViewById(R.id.activity_batch_scanner_et_barcode);
        scannerView = findViewById(R.id.activity_batch_scanner_scanner_view);
        recyclerView = findViewById(R.id.activity_batch_scanner_rv_list);
        btnAdd = findViewById(R.id.activity_batch_scanner_btn_add);
        btnGotoItem = findViewById(R.id.activity_batch_scanner_btn_go_to_item);
        mCodeScanner = new CodeScanner(context, scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        batchListRvAdapter = new BatchListRvAdapter();
        recyclerView.setAdapter(batchListRvAdapter);
        recyclerView.addItemDecoration(new StickHeaderItemDecoration(batchListRvAdapter));
        batchList = new ArrayList<>();
        batchList.add("");
        recyclerView.setVisibility(View.GONE);
        btnGotoItem.setBackgroundColor(getResources().getColor(R.color.disabled_button1));
        btnGotoItem.setTextColor(getResources().getColor(R.color.black));
        btnGotoItem.setEnabled(false);

        intent = getIntent();
        result = (Result) Objects.requireNonNull(intent.getExtras()).get("result");
        result.setDoCSTATUS("Locked");
        batList = (List<String>) Objects.requireNonNull(intent.getExtras()).get("batList");
        itemArrayList = (List<ResultItem>) Objects.requireNonNull(intent.getExtras()).get("itemArrayList");

    }

    private void buttonActions() {
        btnAdd.setOnClickListener(v -> {
            if (!etBarcode.getText().toString().isEmpty()) {
                if (!batchList.contains(etBarcode.getText().toString().trim())) {
                    batchList.add(etBarcode.getText().toString().trim());
                    checkList(etBarcode.getText().toString().trim());
                }
                batchListRvAdapter.setData(this, batchList);
                etBarcode.setText("");
                recyclerView.setVisibility(View.VISIBLE);
                btnGotoItem.setBackgroundColor(getResources().getColor(R.color.app_color));
                btnGotoItem.setTextColor(getResources().getColor(R.color.white));
                btnGotoItem.setEnabled(true);
            }
        });

        batchListRvAdapter.setOnItemClickListener(position -> {
            for (ResultItem resultItem : itemArrayList) {
                int pos = itemArrayList.indexOf(resultItem);
                String batchNum = batchList.get(position);
                if (resultItem.getBatchnum() != null && !resultItem.getBatchnum().isEmpty() &&
                        resultItem.getBatchnum().equalsIgnoreCase(batchNum)) {
                    itemArrayList.get(pos).setStatus("Pending");
                }
            }

            batchList.remove(position);
            batchListRvAdapter.notifyDataSetChanged();
            if (batchList.size() < 2) {
                recyclerView.setVisibility(View.GONE);
                btnGotoItem.setBackgroundColor(getResources().getColor(R.color.disabled_button1));
                btnGotoItem.setTextColor(getResources().getColor(R.color.black));
                btnGotoItem.setEnabled(false);
            }
        });

        btnGotoItem.setOnClickListener(v -> {
            listBarcodeData = "*";
            batchList.remove(0);
            finish();
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

    private void checkList(String barcode) {
        if (!new HashSet<>(batList).containsAll(batchList)) {
            forceLocking(barcode);
        } else {
            for (ResultItem resultItem : itemArrayList) {
                int pos = itemArrayList.indexOf(resultItem);
                String batchNum = resultItem.getBatchnum();
                if (batchList.contains(batchNum)) {
                    itemArrayList.get(pos).setStatus("Released");
                    return;
                }
            }
        }
    }

    private void forceLocking(String wrongBarcode) {
        closeKeyboard(etBarcode);
        result.setWrongBatchno(wrongBarcode);
        mGateViewModel.sendHeaderData(result);
        showMessage("This document has been locked as the batch number : " + wrongBarcode +
                " does not exist. Please contact the administrator.");
    }

    private void showMessage(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (arg0, arg1) -> {
                    isLoadHeader = true;
                    docNoBarcodeData = "";
                    strType = "";
                    finishAffinity();
                    Intent intent = new Intent(context, GateActivity.class);
                    startActivity(intent);
                    finish();
                }).show();
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
        buttonActions();
    }

    @Override
    protected void onPause() {
        //mCodeScanner.releaseResources();
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

    @Override
    public void onPostSuccess(List<LoginModel> loginModel) {

    }

    @Override
    public void onPostFailed(String msg) {

    }
}