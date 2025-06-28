package com.covalsys.ttss_barcode.ui.gate;

import static com.covalsys.ttss_barcode.utils.DateUtils.format_date;
import static com.covalsys.ttss_barcode.utils.DateUtils.format_dd_mm_yy;
import static com.covalsys.ttss_barcode.utils.DateUtils.format_dd_mm_yy_HH_mm;
import static com.covalsys.ttss_barcode.utils.DateUtils.gate_pass_time_format;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.ttss_barcode.BR;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.network.models.HeaderModel.Result;
import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;
import com.covalsys.ttss_barcode.data.network.models.get.gate.AreaOfWorkModel;
import com.covalsys.ttss_barcode.data.network.models.get.gate.SpinnerItem;
import com.covalsys.ttss_barcode.databinding.ActivityGateEntryBinding;
import com.covalsys.ttss_barcode.ui.ViewModelProviderFactory;
import com.covalsys.ttss_barcode.ui.base.BaseActivity;
import com.covalsys.ttss_barcode.ui.gate.activity.ScannerActivity;
import com.covalsys.ttss_barcode.ui.gate.adapter.AreaOfWorkAutoCompleteAdapter;
import com.covalsys.ttss_barcode.ui.gate.adapter.AutoCompleteAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class GateActivity extends BaseActivity<GateViewModel, ActivityGateEntryBinding> implements GateNavigator, TextWatcher {

    @Inject
    ViewModelProviderFactory factory;

    private GateViewModel mGateViewModel;

    public static String docNoBarcodeData = "", listBarcodeData = "", strNoOfDoc = "", strHeaderStatus = "", strType = "", strCustomerType = "", strSecCode = "", strPriority = "", strSecName = "", strOutPassDate = "", strEwayBill = "", strAreaWrk = "", strVehicleNo = "", strRemark = "", strDcValidation = "";
    public static Boolean isDocBarcode = false, disableAllFields = false;
    public BottomSheetBehavior bottomSheetBehavior;
    public BottomSheetDialog bottomSheet;
    public View bottomSheetView;
    public Context context;
    public MaterialButton btnDocScan, btnCancel, btnSubmit;
    public TextInputEditText etDocNo, etDocDate, etOutPassNo, etOutPassDate, etSecurityCode, etSecurityName, etEwayBillNo, etVehicleNo, etRemark, etTotalDoc, etNoDoc; //11
    public MaterialAutoCompleteTextView spnType, spnCustomerType, spnPriority, spnAreaOfWork, spnStatus, spnDcValidation; //6
    public float initialHeight = 0.13f;
    public boolean isSubmitBtnEnabled = false;
    public AutoCompleteAdapter spnTypeAdapter;
    public AreaOfWorkAutoCompleteAdapter spnAreaOfWorkAdapter;
    private Result result = null;
    private String role = "";

    List<AreaOfWorkModel.Result> areaOfWork = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.gateViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_gate_entry;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, GateActivity.class);
    }

    @Override
    public GateViewModel getViewModel() {
        mGateViewModel = new ViewModelProvider(this, factory).get(GateViewModel.class);
        return mGateViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGateViewModel.setNavigator(this);
        initObservables();
    }

    public void initObservables() {
        /*mGateViewModel.error.removeObservers(this);
        mGateViewModel.error.observe(this, this::showSnackBar);*/

        context = this;
        docNoBarcodeData = "";
        strType = "";
        initControls();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        role = mGateViewModel.getPreferenceHelper().getUserType();

        mGateViewModel.progressBar.observe(this, aBoolean -> {
            if (aBoolean) {
               showLoading();
            } else {
               hideLoading();
            }
        });

        mGateViewModel.getHeaderBarCodeLiveData().observe(this, headerBarcodeModel -> {
            if (headerBarcodeModel != null) {
                if (headerBarcodeModel.data.isIsSuccess()) {
                    result = headerBarcodeModel.data.getResult();
                    boolean isPopulateData = true;

                    /*if (!strType.isEmpty() && !strType.equalsIgnoreCase(result.getType() != null ? result.getType() : "")) {
                        isPopulateData = false;
                        showMessage("The selected Type and Doc type are not matching");
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }*/

                    if (result.getDoCSTATUS().equalsIgnoreCase("Locked")) {
                        if (!role.equalsIgnoreCase("SuperAdmin")) {
                            isPopulateData = false;
                            showMessage("This document has been locked, Please contact admin");
                        }
                    }

                    if (isPopulateData) {
                        etDocNo.setText(docNoBarcodeData);
                        etDocNo.setTag(result.getDocentry());
                        etDocDate.setTag("" + (result.getGroupNum() != null ? result.getGroupNum() : 0));
                        if (result.getDocdate() != null) {
                            etDocDate.setText(format_date(gate_pass_time_format, format_dd_mm_yy, result.getDocdate()) + "");
                        }
                        if (result.getOutPassDATE() != null) {
                            etOutPassDate.setText(format_date(gate_pass_time_format, format_dd_mm_yy_HH_mm, result.getOutPassDATE()) + "");
                        } else {
                            Long date = System.currentTimeMillis();
                            SimpleDateFormat dateFormat = new SimpleDateFormat(format_dd_mm_yy_HH_mm, Locale.getDefault());
                            String dateStr = dateFormat.format(date);
                            etOutPassDate.setText(dateStr);
                        }
                        spnType.setText(result.getType() != null ? result.getType() : "");
                        if (result.getOutPassNo() != null) {
                            etOutPassNo.setText(result.getOutPassNo());
                            disableAllFields = !result.getOutPassNo().isEmpty();
                        } else {
                            disableAllFields = false;
                        }
                        spnCustomerType.setText(result.getCusType() != null ? result.getCusType() : "");
                        etSecurityCode.setText(result.getSecCode() != null ? result.getSecCode() : "");
                        spnPriority.setText(result.getPriority() != null ? result.getPriority() : "");
                        etSecurityName.setText(result.getSecName() != null ? result.getSecName() : "");
                        etEwayBillNo.setText(result.getEwayBill() != null ? result.getEwayBill() : "");
                        spnAreaOfWork.setText(result.getAreaOfWkng() != null ? result.getAreaOfWkng() : "");
                        etVehicleNo.setText(result.getVehicleNo() != null ? result.getVehicleNo() : "");
                        if (result.getDoCSTATUS() == null || result.getDoCSTATUS().isEmpty()) {
                            spnStatus.setText("InProgress");
                        } else {
                            spnStatus.setText(result.getDoCSTATUS());
                        }
                        strHeaderStatus = spnStatus.getText().toString();
                        if (result.getRemarks() == null || result.getRemarks().isEmpty()) {
                            etRemark.setText("");
                        } else {
                            etRemark.setText(result.getRemarks());
                        }
                        etTotalDoc.setText("" + (result.getTotDoc() != null && result.getTotDoc() != 0 ? result.getTotDoc() : 1));
                        etNoDoc.setText(result.getNoDoc() != null && result.getNoDoc() != 0 ? result.getNoDoc() + "" : "");
                        spnDcValidation.setText(result.getDcValidation() != null ? result.getDcValidation() : "");

                        strType = result.getType() != null ? result.getType() : "";
                        strOutPassDate = etOutPassDate.getText().toString();
                        strCustomerType = result.getCusType() != null ? result.getCusType() : "";
                        strSecCode = result.getSecCode() != null ? result.getSecCode() : "";
                        strPriority = result.getPriority() != null ? result.getPriority() : "";
                        strSecName = result.getSecName() != null ? result.getSecName() : "";
                        strEwayBill = result.getEwayBill() != null ? result.getEwayBill() : "";
                        strAreaWrk = result.getAreaOfWkng() != null ? result.getAreaOfWkng() : "";
                        strVehicleNo = result.getVehicleNo() != null ? result.getVehicleNo() : "";
                        strRemark = result.getRemarks() != null ? result.getRemarks() : "";
                        strDcValidation = result.getDcValidation() != null ? result.getDcValidation() : "";
                        strNoOfDoc = result.getNoDoc() != 0 ? result.getNoDoc() + "" : "";
                        populateSpinner();
                        if (!docNoBarcodeData.isEmpty()) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }

                        if (disableAllFields) {
                            etDocNo.setEnabled(false);
                            etDocDate.setEnabled(false);
                            etOutPassNo.setEnabled(false);
                            etOutPassDate.setEnabled(false);
                            etSecurityCode.setEnabled(false);
                            etSecurityName.setEnabled(false);
                            etEwayBillNo.setEnabled(false);
                            etVehicleNo.setEnabled(false);
                            etRemark.setEnabled(false);
                            etTotalDoc.setEnabled(false);
                            etNoDoc.setEnabled(false);

                            spnType.setEnabled(false);
                            spnType.setAdapter(null);
                            spnCustomerType.setEnabled(false);
                            spnCustomerType.setAdapter(null);
                            spnPriority.setEnabled(false);
                            spnPriority.setAdapter(null);
                            spnAreaOfWork.setEnabled(false);
                            spnAreaOfWork.setAdapter(null);
                            spnStatus.setEnabled(false);
                            spnStatus.setAdapter(null);
                            spnDcValidation.setEnabled(false);
                            spnDcValidation.setAdapter(null);
                        }
                    }
                } else {
                    Toast.makeText(context, headerBarcodeModel.data.getMessage().getDescription(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initControls() {

        //appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        bottomSheet = new BottomSheetDialog(context);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheet.setContentView(bottomSheetView);
        bottomSheet.setCanceledOnTouchOutside(false);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        btnDocScan = bottomSheetView.findViewById(R.id.bottom_sheet_layout_btn_doc_scan);
        etDocNo = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_doc_no);
        etDocNo.addTextChangedListener(this);
        etDocDate = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_doc_date);
        //etDocDate.addTextChangedListener(this);
        etOutPassNo = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_out_pass_no);
        //etOutPassNo.addTextChangedListener(this);
        etOutPassDate = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_out_pass_date);
        //etOutPassDate.addTextChangedListener(this);
        etSecurityCode = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_security_code);
        etSecurityCode.addTextChangedListener(this);
        etSecurityName = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_security_name);
        etSecurityName.addTextChangedListener(this);
        etEwayBillNo = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_bill_no);
        etEwayBillNo.addTextChangedListener(this);
        etVehicleNo = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_vehicle_no);
        etVehicleNo.addTextChangedListener(this);
        etRemark = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_batch);
        etRemark.addTextChangedListener(this);
        etTotalDoc = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_total_doc);
        //etTotalDoc.addTextChangedListener(this);
        etNoDoc = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_no_of_doc);
        etNoDoc.addTextChangedListener(this);

        //AutoCompleteTextView
        spnType = bottomSheetView.findViewById(R.id.bottom_sheet_layout_spn_type);
        spnType.addTextChangedListener(this);
        spnCustomerType = bottomSheetView.findViewById(R.id.bottom_sheet_layout_spn_customer_type);
        spnCustomerType.addTextChangedListener(this);
        spnPriority = bottomSheetView.findViewById(R.id.bottom_sheet_layout_spn_priority);
        spnPriority.addTextChangedListener(this);
        spnAreaOfWork = bottomSheetView.findViewById(R.id.bottom_sheet_layout_spn_area_of_work);
        spnAreaOfWork.addTextChangedListener(this);
        spnStatus = bottomSheetView.findViewById(R.id.bottom_sheet_layout_spn_status);
        spnStatus.addTextChangedListener(this);
        spnDcValidation = bottomSheetView.findViewById(R.id.bottom_sheet_layout_spn_dc_validation);
        spnDcValidation.addTextChangedListener(this);

        btnCancel = bottomSheetView.findViewById(R.id.bottom_sheet_layout_btn_cancel);
        btnSubmit = bottomSheetView.findViewById(R.id.bottom_sheet_layout_btn_submit);
        btnCancel.setText("Exit");

    }

    private void populateSpinner() {
        spnType.setThreshold(1);
        spnCustomerType.setThreshold(1);
        spnPriority.setThreshold(1);
        spnAreaOfWork.setThreshold(1);
        spnStatus.setThreshold(1);
        spnDcValidation.setThreshold(1);

        ArrayList<SpinnerItem> typeList = new ArrayList<>();
        typeList.add(new SpinnerItem(1, "INV"));
        typeList.add(new SpinnerItem(2, "DC"));
        typeList.add(new SpinnerItem(3, "NRT"));
        typeList.add(new SpinnerItem(4, "RT"));
        spnTypeAdapter = new AutoCompleteAdapter(this, typeList);
        spnType.setAdapter(spnTypeAdapter);

        ArrayList<SpinnerItem> customerTypeList = new ArrayList<>();
        customerTypeList.add(new SpinnerItem(1, "TKM"));
        customerTypeList.add(new SpinnerItem(2, "Non-TKM"));
        spnTypeAdapter = new AutoCompleteAdapter(this, customerTypeList);
        spnCustomerType.setAdapter(spnTypeAdapter);

        ArrayList<SpinnerItem> priorityList = new ArrayList<>();
        priorityList.add(new SpinnerItem(1, "P1"));
        priorityList.add(new SpinnerItem(2, "P2"));
        priorityList.add(new SpinnerItem(3, "-"));
        spnTypeAdapter = new AutoCompleteAdapter(this, priorityList);
        spnPriority.setAdapter(spnTypeAdapter);

        mGateViewModel.fetchAreaOfWork();
        mGateViewModel.getAreaOfWorkLiveData().removeObservers(this);
        mGateViewModel.getAreaOfWorkLiveData().observe(this,databaseModelResource -> {
            if(databaseModelResource.data.isSuccess()){
                areaOfWork = databaseModelResource.data.getResult();
                spnAreaOfWorkAdapter = new AreaOfWorkAutoCompleteAdapter(this, databaseModelResource.data.getResult());
                spnAreaOfWork.setAdapter(spnAreaOfWorkAdapter);
            }else{
                Log.e("TAG", "Area of work no data.");
            }
        });

        ArrayList<SpinnerItem> statusList = new ArrayList<>();
        statusList.add(new SpinnerItem(1, "InProgress"));
        statusList.add(new SpinnerItem(2, "Ready"));
        statusList.add(new SpinnerItem(3, "Cancel"));
        spnTypeAdapter = new AutoCompleteAdapter(this, statusList);
        spnStatus.setAdapter(spnTypeAdapter);

        ArrayList<SpinnerItem> dcValidationList = new ArrayList<>();
        dcValidationList.add(new SpinnerItem(1, "Ok"));
        dcValidationList.add(new SpinnerItem(2, "Not Ok"));
        spnTypeAdapter = new AutoCompleteAdapter(this, dcValidationList);
        spnDcValidation.setAdapter(spnTypeAdapter);

    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void onPostSuccess(List<LoginModel> loginModel) {

    }

    @Override
    public void onPostFailed(String msg) {

    }

    private void showBottomSheet() {

        bottomSheetBehavior.setFitToContents(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        bottomSheetBehavior.setHalfExpandedRatio(initialHeight);
        bottomSheet.show();

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                        bottomSheetBehavior.setHalfExpandedRatio(initialHeight);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:

                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //bottomSheetBehavior.setFitToContents(true);
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        //bottomSheetBehavior.setFitToContents(false);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("bottomSheet ===>>", slideOffset + "");
                if (slideOffset < -0.8) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    bottomSheetBehavior.setHalfExpandedRatio(initialHeight);
                }
            }
        });

        spnType.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    strType = "INV";
                    break;
                case 1:
                    strType = "DC";
                    break;
                case 2:
                    strType = "NRT";
                    break;
                case 3:
                    strType = "RT";
                    break;
            }
        });

        spnCustomerType.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    strCustomerType = "TKM";
                    break;
                case 1:
                    strCustomerType = "Non-TKM";
                    break;
            }
        });

        spnPriority.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    strPriority = "P1";
                    break;
                case 1:
                    strPriority = "P2";
                    break;
                case 2:
                    strPriority = "-";
                    break;
            }
        });



        spnAreaOfWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                strAreaWrk = areaOfWork.get(position).getName();
            }
        });

        spnStatus.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    strHeaderStatus = "InProgress";
                    break;
                case 1:
                    strHeaderStatus = "Ready";
                    break;
                case 2:
                    strHeaderStatus = "Cancel";
                    break;
            }
        });

        spnDcValidation.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    if (spnType.getText().toString().equalsIgnoreCase("RT") || spnType.getText().toString().equalsIgnoreCase("NRT")) {
                        listBarcodeData = "-1";
                    }
                    strDcValidation = "Ok";
                    break;
                case 1:
                    strDcValidation = "Not Ok";
                    if (spnType.getText().toString().equalsIgnoreCase("RT") || spnType.getText().toString().equalsIgnoreCase("NRT")) {
                        if (result.getGroupNum() != null && result.getGroupNum() != 0) {
                            strDcValidation = "Ok";
                            spnDcValidation.setText(strDcValidation);
                            showMessage("The operation is not allowed as all the line items are marked as released");
                            //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                            populateSpinner();
                        }
                    }
                    break;
            }
        });

        bottomSheet.setOnCancelListener(dialog -> {
            bottomSheet = null;
            finish();
            //Toast.makeText(context, "setOnCancelListener", Toast.LENGTH_SHORT).show();
        });

        bottomSheet.setOnDismissListener(dialog -> {
            bottomSheet = null;
            finish();
            //Toast.makeText(context, "setOnDismissListener", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateSubmitBtnState() {
        if (etDocNo.getText().toString().isEmpty()) {
            isSubmitBtnEnabled = false;
            btnSubmit.setBackgroundColor(ContextCompat.getColor(context, R.color.disabled_button));
        } else {
            isSubmitBtnEnabled = true;
            btnSubmit.setBackgroundColor(ContextCompat.getColor(context, R.color.app_color));
        }
        btnSubmit.setEnabled(isSubmitBtnEnabled);
    }

    private void buttonActions() {
        btnCancel.setOnClickListener(v -> {
            exitByBackKey();
        });

        btnSubmit.setOnClickListener(v -> {
            strOutPassDate = etOutPassDate.getText().toString();
            bottomSheet.hide();   //hide();
            Intent intent = new Intent(context, GateListActivity.class);
            intent.putExtra("docEntry", etDocNo.getTag().toString());
            intent.putExtra("groupnum", Integer.valueOf(etDocDate.getTag().toString()));
            intent.putExtra("docNoBarcodeData", docNoBarcodeData);
            startActivity(intent);
            finish();
        });

        btnDocScan.setOnClickListener(v -> {
            bottomSheet.hide();     //hide();
            isDocBarcode = true;
            Intent intent = new Intent(context, ScannerActivity.class);
            startActivity(intent);
        });

        etOutPassDate.setOnClickListener(v -> {
            //TODO Calendar feature
            datePicker();
        });
    }

    private void datePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, monthOfYear, dayOfMonth) -> {
            etOutPassDate.setTag(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (monthOfYear + 1)) + "/" + year1);
            timePicker();
        }, year, month, day);

        datePickerDialog.show();
    }

    private void timePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute1) -> {
            etOutPassDate.setText(etOutPassDate.getTag().toString() + " " + hourOfDay + ":" + minute1);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void fetchData() {
        if (!docNoBarcodeData.isEmpty()) {
            mGateViewModel.getHeaderBarCode(docNoBarcodeData);
            //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
        populateSpinner();
        showBottomSheet();
        updateSubmitBtnState();
        buttonActions();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bottomSheet != null) {
            bottomSheet.dismiss();
        }
    }

    protected void exitByBackKey() {

        new AlertDialog.Builder(this).setTitle("Exit App!").setMessage("Do you want to exit this App?").setPositiveButton("Yes", (arg0, arg1) -> {
            finish();
        }).setNegativeButton("No", (arg0, arg1) -> {

        }).show();

    }

    private void showMessage(String msg) {
        new AlertDialog.Builder(this).setTitle("Alert!").setMessage(msg).setCancelable(false).setPositiveButton("OK", (arg0, arg1) -> {

        }).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateSubmitBtnState();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            if (etSecurityCode.getText().hashCode() == editable.hashCode()) {
                strSecCode = editable.toString();
            }
            if (etSecurityName.getText().hashCode() == editable.hashCode()) {
                strSecName = editable.toString();
            }
            if (etEwayBillNo.getText().hashCode() == editable.hashCode()) {
                strEwayBill = editable.toString();
            }
            if (etVehicleNo.getText().hashCode() == editable.hashCode()) {
                strVehicleNo = editable.toString();
            }
            if (etRemark.getText().hashCode() == editable.hashCode()) {
                strRemark = editable.toString();
            }
            if (etNoDoc.getText().hashCode() == editable.hashCode()) {
                strNoOfDoc = editable.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
