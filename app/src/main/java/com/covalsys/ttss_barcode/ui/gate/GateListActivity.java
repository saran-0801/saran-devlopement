package com.covalsys.ttss_barcode.ui.gate;

import static com.covalsys.ttss_barcode.ui.gate.GateActivity.disableAllFields;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.docNoBarcodeData;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.isDocBarcode;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.listBarcodeData;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strAreaWrk;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strCustomerType;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strDcValidation;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strEwayBill;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strHeaderStatus;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strNoOfDoc;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strOutPassDate;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strPriority;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strRemark;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strSecCode;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strSecName;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strType;
import static com.covalsys.ttss_barcode.ui.gate.GateActivity.strVehicleNo;
import static com.covalsys.ttss_barcode.ui.gate.activity.BatchScannerActivity.batchList;
import static com.covalsys.ttss_barcode.utils.DateUtils.convert_local_to_GMT;
import static com.covalsys.ttss_barcode.utils.DateUtils.format_date;
import static com.covalsys.ttss_barcode.utils.DateUtils.format_dd_mm_yy;
import static com.covalsys.ttss_barcode.utils.DateUtils.format_dd_mm_yy_HH_mm;
import static com.covalsys.ttss_barcode.utils.DateUtils.gate_pass_time_format;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.BR;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.network.models.BarcodeList.ResultItem;
import com.covalsys.ttss_barcode.data.network.models.HeaderModel.Result;
import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;
import com.covalsys.ttss_barcode.data.network.models.get.gate.AreaOfWorkModel;
import com.covalsys.ttss_barcode.data.network.models.get.gate.HeaderData;
import com.covalsys.ttss_barcode.data.network.models.get.gate.SpinnerItem;
import com.covalsys.ttss_barcode.databinding.ActivityListBinding;
import com.covalsys.ttss_barcode.ui.ViewModelProviderFactory;
import com.covalsys.ttss_barcode.ui.base.BaseActivity;
import com.covalsys.ttss_barcode.ui.gate.activity.BatchScannerActivity;
import com.covalsys.ttss_barcode.ui.gate.activity.ScannerActivity;
import com.covalsys.ttss_barcode.ui.gate.adapter.AreaOfWorkAutoCompleteAdapter;
import com.covalsys.ttss_barcode.ui.gate.adapter.AutoCompleteAdapter;
import com.covalsys.ttss_barcode.ui.gate.adapter.RecyclerViewListAdapter;
import com.covalsys.ttss_barcode.ui.gate.adapter.StickHeaderItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

public class GateListActivity extends BaseActivity<GateViewModel, ActivityListBinding> implements GateNavigator, TextWatcher {

    @Inject
    ViewModelProviderFactory factory;

    private GateViewModel appViewModel;

    public static Boolean showBottomSheet = false, isLoadHeader = true;
    public ArrayList<HeaderData> headerList = new ArrayList<>();
    public BottomSheetBehavior bottomSheetBehavior;
    public BottomSheetDialog bottomSheet;
    private RecyclerView recyclerView;
    private Context context;
    private View bottomSheetView;
    private RecyclerViewListAdapter recyclerViewListAdapter;
    private MaterialButton btnDocScan, btnCancel, btnLoad, btnBatchScan, btnNewDocument, btnExit, btnAddOrUpdate, btnShowHeader;
    private TextInputEditText etDocNo, etDocDate, etOutPassNo, etOutPassDate, etSecurityCode, etSecurityName, etEwayBillNo, etVehicleNo, etRemark, etTotalDoc, etNoDoc; //11
    private MaterialAutoCompleteTextView spnType, spnCustomerType, spnPriority, spnAreaOfWork, spnStatus, spnDcValidation; //6
    private AutoCompleteAdapter spnTypeAdapter;
    private AreaOfWorkAutoCompleteAdapter spnAreaOfWorkAdapter;
    private FloatingActionButton fabDocScan;
    private List<ResultItem> itemArrayList;
    private Dialog dialog;
    private Boolean isBarCodeListLoaded = false, isDocumentLocked = false;
    private Integer totalDoc = 0;
    private Double totalPsc = 0.0, totalNetWt = 0.0, totalGrossWt = 0.0;
    private LinearLayout linearLayoutRecyclerView, layout_rv;
    private TextView tvPsc, tvNetWt, tvGrossWt, tvOutPassNo, tvOutPassDate;
    //private HeaderBarcodeModel.Result result;
    private Result result;
    private SharedPreferences sharedPreferences;
    private boolean isPopulateData = true, isDupli = false, isFromShowHeader = false, isNeedRemove = true;
    private String role = "";

    List<AreaOfWorkModel.Result> areaOfWork = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.gateListViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_list;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, GateListActivity.class);
    }

    @Override
    public GateViewModel getViewModel() {
        appViewModel = new ViewModelProvider(this, factory).get(GateViewModel.class);
        return appViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel.setNavigator(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initObservables();
    }

    public void initObservables() {
        context = this;
        appViewModel.progressBar.observe(this, aBoolean -> {
            if (aBoolean) {
               showLoading();
            } else {
               hideLoading();
            }
        });

        initControls();

        appViewModel.getHeaderBarCodeLiveData().observe(this, headerBarcodeModel -> {
            if (headerBarcodeModel != null) {
                if (headerBarcodeModel.data.isIsSuccess()) {
                    result = headerBarcodeModel.data.getResult();
                    isPopulateData = true;
                    isDupli = false;
                    boolean isInvalid = false;
                    if (isLoadHeader) {
                        for (HeaderData res : headerList) {
                            if (res.getDocentry().equals(result.getDocentry())) {
                                isPopulateData = false;
                                isDupli = true;
                            }
                        }

                        if (result.getDoCSTATUS().equalsIgnoreCase("Locked")) {
                            if (!role.equalsIgnoreCase("superAdmin")) {
                                isPopulateData = false;
                                isDupli = false;
                                isInvalid = true;
                                docNoBarcodeData = "";
                                showMessage("This document has been locked, Please contact admin");
                            }
                        }

                        if (!strType.isEmpty() && !strType.equalsIgnoreCase(result.getType())) {
                            isPopulateData = false;
                            isDupli = false;
                            isInvalid = true;
                            docNoBarcodeData = "";
                            showMessage("The selected Type and Doc type are not matching");
                            //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        }

                        if (headerList.size() > 0) {
                            if (result.getOutPassNo() != null && !result.getOutPassNo().isEmpty() || result.getGroupNum() != null && result.getGroupNum() != 0) {
                                if (!isInvalid) {
                                    docNoBarcodeData = "";
                                    showMessage("Document already is in InProgress state");
                                    //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                                }
                                isInvalid = true;
                                isPopulateData = false;
                                isDupli = false;
                            }
                        }

                        if (!isDupli) {
                            if (!isInvalid) {
                                if (result.getTotDoc() == null || result.getTotDoc() == 0) {
                                    result.setTotDoc(1);
                                }
                                totalDoc = totalDoc + result.getTotDoc();
                            }
                        } else {
                            isPopulateData = true;
                            if (!isFromShowHeader) {
                                isPopulateData = false;
                                if (!isInvalid) {
                                    docNoBarcodeData = "";
                                    showMessage("Duplicate Entry");
                                    //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    etTotalDoc.setText(String.valueOf(totalDoc));

                    if (isDupli || isPopulateData) {
                        if (result.getGroupNum() != null && result.getGroupNum() != 0) {
                            btnAddOrUpdate.setText("Update");
                            fabDocScan.hide();
                        } else {
                            fabDocScan.show();
                            btnAddOrUpdate.setText("Add");
                            if (strHeaderStatus.equalsIgnoreCase("Ready") || strHeaderStatus.equalsIgnoreCase("Cancel")) {
                                fabDocScan.hide();
                            }
                        }

                        etDocNo.setTag(result.getDocentry() + "");
                        etDocDate.setTag(result.getGroupNum() != null ? result.getGroupNum() : 0);
                        if (result.getDocdate() != null) {
                            etDocDate.setText(format_date(gate_pass_time_format, format_dd_mm_yy, result.getDocdate()) + "");
                        }

                        etOutPassDate.setText(strOutPassDate);
                        spnType.setText(strType);
                        if (result.getOutPassNo() != null) {
                            etOutPassNo.setText(result.getOutPassNo());
                            disableAllFields = !result.getOutPassNo().isEmpty();
                        } else {
                            disableAllFields = false;
                        }

                        spnCustomerType.setText(strCustomerType);
                        etSecurityCode.setText(strSecCode);
                        spnPriority.setText(strPriority);
                        etSecurityName.setText(strSecName);
                        etEwayBillNo.setText(strEwayBill);
                        spnAreaOfWork.setText(strAreaWrk);
                        etVehicleNo.setText(strVehicleNo);
                        spnStatus.setText(strHeaderStatus);
                        etRemark.setText(strRemark);
                        etNoDoc.setText(strNoOfDoc);
                        spnDcValidation.setText(strDcValidation);

                    } else {

                        if (result.getDocdate() != null) {
                            etDocDate.setText(format_date(gate_pass_time_format, format_dd_mm_yy, result.getDocdate()) + "");
                        }

                        etOutPassDate.setText(format_date(gate_pass_time_format, format_dd_mm_yy_HH_mm, result.getOutPassDATE()));
                        spnType.setText(result.getType());
                        if (result.getOutPassNo() != null) {
                            etOutPassNo.setText(result.getOutPassNo());
                        }

                        spnCustomerType.setText(result.getCusType());
                        etSecurityCode.setText(result.getSecCode());
                        spnPriority.setText(result.getPriority());
                        etSecurityName.setText(result.getSecName());
                        etEwayBillNo.setText(result.getEwayBill());
                        spnAreaOfWork.setText(result.getAreaOfWkng());
                        etVehicleNo.setText(result.getVehicleNo());
                        spnStatus.setText(result.getDoCSTATUS());
                        etRemark.setText(result.getRemarks());
                        etNoDoc.setText(result.getNoDoc() + "");
                        spnDcValidation.setText(result.getDcValidation());
                    }

                    populateSpinner();

                    if (itemArrayList == null) {
                        itemArrayList = new ArrayList<>();
                        itemArrayList.add(new ResultItem("-777", "Released"));
                        appViewModel.getBarCodeList(getIntent().getStringExtra("docEntry"), getIntent().getIntExtra("groupnum", 0));
                    } else {
                        if (!listBarcodeData.isEmpty()) {
                            int validBatchCount = 0;
                            String type = spnType.getText().toString();
                            String dcValidation = spnDcValidation.getText().toString();

                            for (ResultItem resultItem : itemArrayList) {
                                int pos = itemArrayList.indexOf(resultItem);
                                String batchNum = resultItem.getBatchnum();

                                if (batchList.contains(batchNum)) {
                                    itemArrayList.get(pos).setStatus("Released");
                                    validBatchCount++;

                                    if ((type.equalsIgnoreCase("RT") ||
                                            type.equalsIgnoreCase("NRT")) &&
                                            (dcValidation.equalsIgnoreCase("ok") ||
                                                    dcValidation.equalsIgnoreCase("not ok"))) {
                                        itemArrayList.get(pos).setStatus(dcValidation.equalsIgnoreCase("ok") ? "Released" : "Pending");
                                    }
                                }
                            }

                            recyclerViewListAdapter.setData(context, itemArrayList);
                            listBarcodeData = "";
                            if (batchList.size() != validBatchCount) {
                                isDocumentLocked = true;
                                sendHeaderData();
                                showMessage("This document has been locked. Please contact admin.");
                            }
                        }
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

                        btnBatchScan.setTextColor(getResources().getColor(R.color.black));
                        btnBatchScan.setEnabled(false);
                        btnBatchScan.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.disabled_button1)));
                        btnBatchScan.setStrokeWidth(4);

                        btnAddOrUpdate.setBackgroundColor(getResources().getColor(R.color.disabled_button1));
                        btnAddOrUpdate.setTextColor(getResources().getColor(R.color.black));
                        btnAddOrUpdate.setEnabled(false);
                    }

                } else {
                    Toast.makeText(context, headerBarcodeModel.data.getMessage().getDescription(), Toast.LENGTH_SHORT).show();
                    isPopulateData = false;
                }
            } else {
                showMessage("Invalid document number");
                //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }
            updateSubmitBtnState();
        });

        appViewModel.getBarCodeListLiveData().observe(this, barcodeListModel -> {
            if (barcodeListModel != null) {
                if (barcodeListModel.data.isIsSuccess()) {
                    if (itemArrayList == null) {
                        itemArrayList = new ArrayList<>();
                        itemArrayList.add(new ResultItem("-777", "Released"));
                    }
                    itemArrayList.addAll(barcodeListModel.data.getResult());

                    if (spnType.getText().toString().equalsIgnoreCase("RT") || spnType.getText().toString().equalsIgnoreCase("NRT")) {
                        for (ResultItem resultItem : itemArrayList) {
                            int dcPos = itemArrayList.indexOf(resultItem);
                            if (spnDcValidation.getText().toString().equalsIgnoreCase("ok")) {
                                itemArrayList.get(dcPos).setStatus("Released");
                            }
                            if (spnDcValidation.getText().toString().equalsIgnoreCase("not ok")) {
                                itemArrayList.get(dcPos).setStatus("Pending");
                            }
                        }
                    }
                    listBarcodeData = "";
                    recyclerViewListAdapter.setData(context, itemArrayList);
                    if (isLoadHeader) {
                        totalPsc = totalPsc + result.getTotalPCS();
                        totalNetWt = totalNetWt + result.getTotalNETWT();
                        totalGrossWt = totalGrossWt + result.getTotalGROSSWT();
                    }
                    isLoadHeader = true;
                    tvPsc.setText(totalPsc + "");
                    tvNetWt.setText(totalNetWt + "");
                    tvGrossWt.setText(totalGrossWt + "");

                    List<HeaderData> tempHeaderList = new ArrayList<>();
                    for (ResultItem resultItem : itemArrayList) {
                        try {
                            if (!resultItem.getItemcode().equalsIgnoreCase("-777")) {
                                tempHeaderList.add(new HeaderData(resultItem.getDocNum(), resultItem.getDocentry()));
                            }
                        } catch (Exception e) {
                            tempHeaderList.add(new HeaderData(resultItem.getDocNum(), resultItem.getDocentry()));
                        }
                    }
                    ArrayList<HeaderData> myList = new ArrayList<>();
                    HashSet<String> mySet = new HashSet<>();

                    // Add elements to the list, checking for duplicates
                    for (HeaderData element : tempHeaderList) {
                        if (mySet.add(element.getDocNum())) {
                            // Element was not already in the set, so add it to the list
                            myList.add(element);
                        }
                    }
                    headerList.addAll(myList);
                } else {
                    Toast.makeText(context, barcodeListModel.data.getMessage().getDescription(), Toast.LENGTH_SHORT).show();
                }
                linearLayoutRecyclerView.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
            }
        });

        appViewModel.getHeaderPostDataLiveData().observe(this, postHeaderModel -> {
            if (postHeaderModel != null) {
                if (postHeaderModel.data.isIsSuccess()) {
                    // TODO Want to call the second
                    //postHeaderModel.getResult()
                    if (isNeedRemove) {
                        itemArrayList.remove(0);
                    }
                    isNeedRemove = true;
                    appViewModel.sendBarCodeList(itemArrayList, ((Double) postHeaderModel.data.getResult()).intValue());
                } else {
                    showDialogue(false);
                    //Toast.makeText(context, postHeaderModel.getMessage().getDescription(), Toast.LENGTH_SHORT).show();
                }
            } else {
                showDialogue(false);
                //Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
            }
        });

        appViewModel.getListPostDataLiveData().observe(this, postListModel -> {
            if (postListModel != null) {
                if (postListModel.data.isIsSuccess()) {
                    showDialogue(true);
                    if (postListModel.data.getResult().getOutPassNo() != null && !postListModel.data.getResult().getOutPassNo().isEmpty()) {
                        if (spnStatus.getText().toString().equalsIgnoreCase("Cancel")) {
                            tvOutPassNo.setVisibility(View.GONE);
                            tvOutPassDate.setVisibility(View.GONE);
                        } else {
                            tvOutPassNo.setText("Out Pass No. : " + postListModel.data.getResult().getOutPassNo());
                            tvOutPassDate.setText("Out Pass Date. : " + format_date(gate_pass_time_format, format_dd_mm_yy_HH_mm, postListModel.data.getResult().getOutPassDATE()));
                        }
                    } else {
                        tvOutPassNo.setVisibility(View.GONE);
                        tvOutPassDate.setVisibility(View.GONE);
                    }
                } else {
                    showDialogue(false);
                    //Toast.makeText(context, postListModel.getMessage().getDescription(), Toast.LENGTH_SHORT).show();
                }
            } else {
                showDialogue(false);
                //Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initControls() {
        //sharedPreferences = getSharedPreferences(SHRD_PREF, Context.MODE_PRIVATE);
        //appViewModel = new ViewModelProvider(this).get(GateViewModel.class);
        btnBatchScan = findViewById(R.id.activity_list_btn_batch_scan);
        fabDocScan = findViewById(R.id.activity_list_fab_doc_scan);
        btnNewDocument = findViewById(R.id.activity_list_btn_new_document);
        btnExit = findViewById(R.id.activity_list_btn_exit);
        btnAddOrUpdate = findViewById(R.id.activity_list_btn_add_or_update);
        btnShowHeader = findViewById(R.id.activity_list_btn_show_header);
        linearLayoutRecyclerView = findViewById(R.id.activity_list_ll_rv);
        layout_rv = findViewById(R.id.activity_list_layout_rv);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.4);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        int margin = (int) getResources().getDimension(R.dimen.margins); // define the margin size in resources
        params.topMargin = margin;
        params.bottomMargin = 0;
        params.leftMargin = margin;
        params.rightMargin = margin;
        layout_rv.setLayoutParams(params);
        linearLayoutRecyclerView.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.activity_list_rv_list);
        tvPsc = findViewById(R.id.activity_list_tv_total_psc);
        tvNetWt = findViewById(R.id.activity_list_tv_total_net_wt);
        tvGrossWt = findViewById(R.id.activity_list_tv_total_gross_wt);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewListAdapter = new RecyclerViewListAdapter();
        recyclerView.setAdapter(recyclerViewListAdapter);
        recyclerView.addItemDecoration(new StickHeaderItemDecoration(recyclerViewListAdapter));

    }

    private void updateSubmitBtnState() {
        boolean isSubmitBtnEnabled = false;
        if (etDocNo.getText().toString().isEmpty() || !isPopulateData) {
            btnLoad.setBackgroundColor(ContextCompat.getColor(context, R.color.disabled_button));
        } else {
            isSubmitBtnEnabled = true;
            btnLoad.setBackgroundColor(ContextCompat.getColor(context, R.color.app_color));
        }
        btnLoad.setEnabled(isSubmitBtnEnabled);
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

        /*ArrayList<SpinnerItem> areaOfWorkList = new ArrayList<>();
        areaOfWorkList.add(new SpinnerItem(1, "MotherCoil"));
        areaOfWorkList.add(new SpinnerItem(2, "SlitCoil"));
        areaOfWorkList.add(new SpinnerItem(3, "Dispatch"));
        areaOfWorkList.add(new SpinnerItem(4, "Warehouse"));
        spnTypeAdapter = new AutoCompleteAdapter(this, areaOfWorkList);
        spnAreaOfWork.setAdapter(spnTypeAdapter);*/

        appViewModel.fetchAreaOfWork();
        appViewModel.getAreaOfWorkLiveData().removeObservers(this);
        appViewModel.getAreaOfWorkLiveData().observe(this,databaseModelResource -> {
            if(databaseModelResource.data.isSuccess()){
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

    private void showBottomSheet() {
        bottomSheet = new BottomSheetDialog(context);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);
        bottomSheet.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setFitToContents(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheet.hide();
        //TextInputEditText //MaterialButton
        etDocNo = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_doc_no);
        etDocNo.addTextChangedListener(this);
        etDocDate = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_doc_date);
        //etDocDate.addTextChangedListener(this);
        etOutPassNo = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_out_pass_no);
        //etOutPassNo.addTextChangedListener(this);
        etOutPassDate = bottomSheetView.findViewById(R.id.bottom_sheet_layout_et_out_pass_date);
        //etOutPassDate.addTextChangedListener(this);x0310200cp
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

        btnDocScan = bottomSheetView.findViewById(R.id.bottom_sheet_layout_btn_doc_scan);
        btnCancel = bottomSheetView.findViewById(R.id.bottom_sheet_layout_btn_cancel);
        btnLoad = bottomSheetView.findViewById(R.id.bottom_sheet_layout_btn_submit);

        btnDocScan.setVisibility(View.GONE);

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
                    if (result.getGroupNum() != null && result.getGroupNum() != 0) {
                        fabDocScan.hide();
                    } else {
                        fabDocScan.show();
                    }
                    break;
                case 1:
                    strHeaderStatus = "Ready";
                    boolean isAllReleased = true;
                    for (ResultItem item : itemArrayList) {
                        if (item.getStatus() == null || !item.getStatus().equalsIgnoreCase("Released")) {
                            isAllReleased = false;
                            break;
                        }
                    }
                    if (!isAllReleased) {
                        spnStatus.setText("InProgress");
                        strHeaderStatus = "InProgress";
                        showMessage("You can't change the status to ready until all batch released");
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        if (result.getGroupNum() != null && result.getGroupNum() != 0) {
                            fabDocScan.hide();
                        } else {
                            fabDocScan.show();
                        }
                        populateSpinner();
                    } else {
                        fabDocScan.hide();
                    }
                    break;
                case 2:
                    strHeaderStatus = "Cancel";
                    fabDocScan.hide();
                    break;
            }
        });

        spnDcValidation.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    strDcValidation = "Ok";
                    break;
                case 1:
                    strDcValidation = "Not Ok";
                    if (spnType.getText().toString().equalsIgnoreCase("RT") || spnType.getText().toString().equalsIgnoreCase("NRT")) {
                        if (result.getGroupNum() != null && result.getGroupNum() != 0) {
                            strDcValidation = "Ok";
                            spnDcValidation.setText(strDcValidation);
                            showMessage("Documents already released");
                            //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                            populateSpinner();
                        }
                    }

                    break;
            }
            if (spnType.getText().toString().equalsIgnoreCase("RT") || spnType.getText().toString().equalsIgnoreCase("NRT")) {
                for (ResultItem resultItem : itemArrayList) {
                    int dcPos = itemArrayList.indexOf(resultItem);
                    if (strDcValidation.equalsIgnoreCase("ok")) {
                        itemArrayList.get(dcPos).setStatus("Released");
                    } else {
                        itemArrayList.get(dcPos).setStatus("Pending");
                    }
                }
                recyclerViewListAdapter.setData(context, itemArrayList);
            }
        });

        bottomSheet.setOnCancelListener(dialog -> {
            isBarCodeListLoaded = false;
        });

        bottomSheet.setOnDismissListener(dialog -> {
            isBarCodeListLoaded = false;
        });
    }

    private void fetchData() {

        if (docNoBarcodeData.isEmpty()) {
            docNoBarcodeData = getIntent().getStringExtra("docNoBarcodeData");
        }

        role = appViewModel.getPreferenceHelper().getUserType();
        appViewModel.getHeaderBarCode(docNoBarcodeData);
        etDocNo.setText(docNoBarcodeData);

        if (showBottomSheet) {
            bottomSheet.show();
            showBottomSheet = false;
        }
    }

    private void buttonActions() {

        btnCancel.setOnClickListener(v -> { // cancel inside bottom_sheet
            etTotalDoc.setText(totalDoc + "");
            bottomSheet.hide();
            isBarCodeListLoaded = false;
            isLoadHeader = true;
        });

        fabDocScan.setOnClickListener(v -> {
            isFromShowHeader = false;
            isLoadHeader = true;
            showBottomSheet = true;
            isDocBarcode = true;
            Intent intent = new Intent(context, ScannerActivity.class);
            startActivity(intent);
        });

        btnBatchScan.setOnClickListener(v -> { // list item scanning
            isLoadHeader = false;
            isDocBarcode = false;
            result.setUUserName(appViewModel.getPreferenceHelper().getFirstName());
            result.setType(spnType.getText().toString());
            result.setOutPassDATE(convert_local_to_GMT(etOutPassDate.getText().toString(), format_dd_mm_yy_HH_mm, gate_pass_time_format));
            result.setCusType(spnCustomerType.getText().toString());
            result.setSecCode(etSecurityCode.getText().toString());
            result.setPriority(spnPriority.getText().toString());
            result.setSecName(etSecurityName.getText().toString());
            result.setEwayBill(etEwayBillNo.getText().toString());
            result.setAreaOfWkng(spnAreaOfWork.getText().toString());
            result.setVehicleNo(etVehicleNo.getText().toString());
            result.setDoCSTATUS(isDocumentLocked ? "Locked" : spnStatus.getText().toString());
            result.setType(spnType.getText().toString());
            result.setRemarks(etRemark.getText().toString());
            result.setType(spnType.getText().toString());
            result.setNoDoc(etNoDoc.getText().toString().isEmpty() ? 0 : Integer.valueOf(etNoDoc.getText().toString()));
            result.setTotDoc(etTotalDoc.getText().toString().isEmpty() ? 1 : Integer.valueOf(etTotalDoc.getText().toString()));
            result.setDcValidation(spnDcValidation.getText().toString());
            result.setTotalPCS(Double.valueOf(tvPsc.getText().toString()));
            result.setTotalNETWT(Double.valueOf(tvNetWt.getText().toString()));
            result.setTotalGROSSWT(Double.valueOf(tvGrossWt.getText().toString()));

            List<String> batList = new ArrayList<>();
            for (ResultItem resultItem : itemArrayList) {
                if (resultItem.getBatchnum() != null && !resultItem.getBatchnum().isEmpty()) {
                    batList.add(resultItem.getBatchnum());
                } else {
                    batList.add("");
                }
            }

            Intent intent = new Intent(context, BatchScannerActivity.class);
            intent.putExtra("result", result);
            intent.putExtra("batList", (Serializable) batList);
            intent.putExtra("itemArrayList", (Serializable) itemArrayList);
            startActivity(intent);
        });

        btnExit.setOnClickListener(v -> {
            exitByBackKey();
        });

        btnNewDocument.setOnClickListener(v -> {
            isLoadHeader = true;
            docNoBarcodeData = "";
            strType = "";
            finishAffinity();
            Intent intent = new Intent(context, GateActivity.class);
            startActivity(intent);
            finish();
        });

        btnLoad.setOnClickListener(v -> {// Submit inside bottom_sheet
            bottomSheet.hide();
            if (!isBarCodeListLoaded) {
                appViewModel.getBarCodeList(etDocNo.getTag().toString(), Integer.valueOf(etDocDate.getTag().toString()));
            }
            isBarCodeListLoaded = false;
        });

        btnShowHeader.setOnClickListener(v -> {
            isBarCodeListLoaded = true;
            bottomSheet.show();
            if (!isPopulateData) {
                isFromShowHeader = true;
                appViewModel.getHeaderBarCode(getIntent().getStringExtra("docNoBarcodeData"));
                etDocNo.setText(getIntent().getStringExtra("docNoBarcodeData"));
            }
        });

        btnAddOrUpdate.setOnClickListener(v -> {
            if (strHeaderStatus.equalsIgnoreCase("Ready")) {
                boolean isAllReleased = true;
                for (ResultItem item : itemArrayList) {
                    if (item.getStatus() == null || !item.getStatus().equalsIgnoreCase("Released")) {
                        isAllReleased = false;
                        break;
                    }
                }
                if (isAllReleased) {
                    sendHeaderData();
                } else {
                    showMessage("Doc Status can't be Ready until all the batch items are Released");
                    //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            } else {
                sendHeaderData();
            }
        });

        etOutPassDate.setOnClickListener(v -> {
            datePicker();
        });
    }

    private void sendHeaderData() {
        boolean isAllSet = true;
        if (!isDocumentLocked) {
            if (etNoDoc.getText().toString().isEmpty()) {
                Toast.makeText(context, "No of Doc Field is Empty", Toast.LENGTH_SHORT).show();
                isAllSet = false;
            } else {
                if (!Integer.valueOf(etTotalDoc.getText().toString()).equals(Integer.valueOf(etNoDoc.getText().toString()))) {
                    Toast.makeText(context, "Mismatch in Total Doc and No of Doc", Toast.LENGTH_SHORT).show();
                    isAllSet = false;
                }
            }

            if (etSecurityName.getText().toString().isEmpty()) {
                Toast.makeText(context, "Security Name Field is Empty", Toast.LENGTH_SHORT).show();
                isAllSet = false;
            }

            if (etSecurityCode.getText().toString().isEmpty()) {
                Toast.makeText(context, "Security Code Field is Empty", Toast.LENGTH_SHORT).show();
                isAllSet = false;
            }

            if (etVehicleNo.getText().toString().isEmpty()) {
                Toast.makeText(context, "Vehicle No Field is Empty", Toast.LENGTH_SHORT).show();
                isAllSet = false;
            }

            if (etEwayBillNo.getText().toString().isEmpty() && !spnType.getText().toString().equalsIgnoreCase("RT")) {
                Toast.makeText(context, "E-way bill No Field is Empty", Toast.LENGTH_SHORT).show();
                isAllSet = false;
            }

            if (strAreaWrk.isEmpty()) {
                Toast.makeText(context, "Area Of Work Field is Empty", Toast.LENGTH_SHORT).show();
                isAllSet = false;
            }
        }

        if (isAllSet) {
            result.setUUserName(appViewModel.getPreferenceHelper().getFirstName());
            result.setType(spnType.getText().toString());
            result.setOutPassDATE(convert_local_to_GMT(etOutPassDate.getText().toString(), format_dd_mm_yy_HH_mm, gate_pass_time_format));
            result.setCusType(spnCustomerType.getText().toString());
            result.setSecCode(etSecurityCode.getText().toString());
            result.setPriority(spnPriority.getText().toString());
            result.setSecName(etSecurityName.getText().toString());
            result.setEwayBill(etEwayBillNo.getText().toString());
            result.setAreaOfWkng(spnAreaOfWork.getText().toString());
            result.setVehicleNo(etVehicleNo.getText().toString());
            result.setDoCSTATUS(isDocumentLocked ? "Locked" : spnStatus.getText().toString());
            result.setType(spnType.getText().toString());
            result.setRemarks(etRemark.getText().toString());
            result.setType(spnType.getText().toString());
            result.setNoDoc(etNoDoc.getText().toString().isEmpty() ? 0 : Integer.valueOf(etNoDoc.getText().toString()));
            result.setTotDoc(etTotalDoc.getText().toString().isEmpty() ? 1 : Integer.valueOf(etTotalDoc.getText().toString()));
            result.setDcValidation(spnDcValidation.getText().toString());
            result.setTotalPCS(Double.valueOf(tvPsc.getText().toString()));
            result.setTotalNETWT(Double.valueOf(tvNetWt.getText().toString()));
            result.setTotalGROSSWT(Double.valueOf(tvGrossWt.getText().toString()));

            appViewModel.sendHeaderData(result);
        }
    }

    private void showDialogue(Boolean isSuccess) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogue_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (!isDocumentLocked) {
            dialog.show();
        }
        ImageView imageView = dialog.findViewById(R.id.dialogue_layout_iv_icon);
        TextView tvDialogTitle = dialog.findViewById(R.id.dialogue_layout_tv_title);
        MaterialButton btnExit = dialog.findViewById(R.id.dialogue_layout_btn_exit);
        MaterialButton btnNewOrRetry = dialog.findViewById(R.id.dialogue_layout_btn_new_document);
        tvOutPassNo = dialog.findViewById(R.id.dialogue_layout_tv_out_pass_num);
        tvOutPassDate = dialog.findViewById(R.id.dialogue_layout_tv_out_pass_date);

        if (isSuccess) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.success));
            tvDialogTitle.setText("Document Updated Successfully!");
            tvOutPassNo.setVisibility(View.VISIBLE);
            tvOutPassDate.setVisibility(View.VISIBLE);
        } else {
            tvOutPassNo.setVisibility(View.GONE);
            tvOutPassDate.setVisibility(View.GONE);
            imageView.setImageDrawable(context.getDrawable(R.drawable.error));
            tvDialogTitle.setText("Error Updating Document!");
            btnNewOrRetry.setText("Retry");
        }

        btnNewOrRetry.setOnClickListener(v -> {
            if (isSuccess) {
                isLoadHeader = true;
                docNoBarcodeData = "";
                finishAffinity();
                Intent intent = new Intent(context, GateActivity.class);
                startActivity(intent);
                finish();
            } else {
                isNeedRemove = false;
                dialog.dismiss();
            }
        });

        btnExit.setOnClickListener(v -> {
            //dialog.dismiss();
            exitByBackKey();
        });
    }

    protected void exitByBackKey() {

        /*Intent intents = new Intent(A.this, B.class);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);  //IntentCompat.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intents);
        finish();*/
        /*new AlertDialog.Builder(this).setTitle("Exit App!").setMessage("Do you want to exit this App?").setPositiveButton("Yes", (arg0, arg1) -> {
            if (dialog != null) {
                dialog.dismiss();
            }
            strType = "";
            finishAffinity();
        }).setNegativeButton("No", (arg0, arg1) -> {
        }).show();*/

    }

    private void showMessage(String msg) {
        new AlertDialog.Builder(this).setTitle("Alert!").setMessage(msg).setCancelable(false).setPositiveButton("OK", (arg0, arg1) -> {
            if (msg.contains("admin")) {
                isLoadHeader = true;
                docNoBarcodeData = "";
                strType = "";
                finishAffinity();
                Intent intent = new Intent(context, GateActivity.class);
                startActivity(intent);
                finish();
            }
        }).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        showBottomSheet();
        buttonActions();
        fetchData();
        populateSpinner();
        //updateSubmitBtnState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if(bottomSheet != null) {
            bottomSheet.dismiss();
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bottomSheet != null) {
            bottomSheet.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        exitByBackKey();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            if (etDocNo.getText().hashCode() == editable.hashCode()) {
                updateSubmitBtnState();
            }
            if (isPopulateData) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPostSuccess(List<LoginModel> loginModel) {

    }

    @Override
    public void onPostFailed(String msg) {

    }
}
