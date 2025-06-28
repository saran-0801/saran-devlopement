package com.covalsys.ttss_barcode.ui.asset_tag;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.covalsys.ttss_barcode.BR;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.database.entities.AssetCountLine;
import com.covalsys.ttss_barcode.data.database.entities.AssetHeader;
import com.covalsys.ttss_barcode.databinding.FragmentAssetTagBinding;
import com.covalsys.ttss_barcode.ui.base.BaseFragment;
import com.covalsys.ttss_barcode.ui.scan.ScanActivity;
import com.covalsys.ttss_barcode.ui.scan.asset.AssetCountBatchScanActivity;
import com.covalsys.ttss_barcode.utils.DateUtils;
import com.covalsys.ttss_inv.data.network.models.get.GetAssetDocumentModel;

import java.util.List;

import javax.inject.Inject;

public class AssetCountingFragment extends BaseFragment<AssetCountingViewModel, FragmentAssetTagBinding> implements AssetCountingNavigator
        ,AssetCountingAdapter.Callback {


    public static final String TAG = AssetCountingFragment.class.getSimpleName();

    @Inject
    AssetCountingAdapter countingAdapter;

    String locationCode;
    static String mNotifCount = "0";
    static Button notifCount;

    @Override
    public int getBindingVariable() {
        return BR.assetCountViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_asset_tag;
    }

    @Override
    protected Class<AssetCountingViewModel> getViewModel() {
        return AssetCountingViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //setHasOptionsMenu(true);
        viewModel.setNavigator(this);
        countingAdapter.setOnClick(this);
        setUp();
        return dataBinding.getRoot();
    }

    private void setUp() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Asset Tag");
        }

        viewModel.progressBar.observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        viewModel.setContext(getActivity());

        LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.recyclerView.setLayoutManager(primaryManager);
        dataBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.recyclerView.setAdapter(countingAdapter);

        dataBinding.frStockIvLocationScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLaunch.launch(new Intent(requireActivity(),  ScanActivity.class).putExtra("DI", 1));
            }
        });

        dataBinding.layScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLaunch.launch(new Intent(requireActivity(),  AssetCountBatchScanActivity.class).putExtra("DI", 1));
            }
        });

        dataBinding.frStockTvLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(String.valueOf(dataBinding.etLocation.getText()).isEmpty()){
                    showSnackBar("Location empty!...");
                }else {
                    viewModel.getDocument(String.valueOf(dataBinding.etLocation.getText()),"A");
                }
            }
        });

        dataBinding.etLocation.requestFocus();
        dataBinding.etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataBinding.etLocation.getText().hashCode() == s.hashCode()) {
                    locationCode = s.toString();
                }
            }
        });

        dataBinding.layAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pCount = viewModel.getDatabase().assetCountLineDao().getPendingDataCount();
                if(viewModel.getDatabase().assetCountLineDao().getDataSize() == 0) {
                    showSnackBar("Invalid document not able to save!...");
                }else if(pCount != 0) {
                    String text = "";
                    for(AssetCountLine line : viewModel.getDatabase().assetCountLineDao().getPendingDataList()) {
                        text += line.getItemCode()+",";
                    }

                    new AlertDialog.Builder(getActivity())
                            .setMessage("Following items are not scanned, Are you sure want to Add?\n "+ text)
                            .setCancelable(false)
                            .setPositiveButton("Add", (dialog, id) -> {
                                dataBinding.layScan.setEnabled(false);
                                dataBinding.layAdd.setEnabled(false);
                                dataBinding.layCancel.setEnabled(false);
                                postToServer("", locationCode, DateUtils.currentDateYYYY(), "O");
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    /*new AlertDialog.Builder(getActivity())
                            .setMessage("Following items are not scanned, complete scanning then continue posting.\n "+ text)
                            .setCancelable(false)
                            .setPositiveButton("Okay", (dialog, id) -> {

                            })
                            .show();*/
                } else {

                    dataBinding.layScan.setEnabled(false);
                    dataBinding.layAdd.setEnabled(false);
                    dataBinding.layCancel.setEnabled(false);
                    postToServer("", locationCode, DateUtils.currentDateYYYY(),"C");
                }
            }
        });

        dataBinding.layCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure want cancel this document?...")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            viewModel.getDatabase().assetCountLineDao().deleteAllData();
                            viewModel.getDatabase().assetCountScanLineDao().deleteAllData();
                            viewModel.getDatabase().assetHeaderDao().deleteAllData();
                            dataBinding.etLocation.setText("");
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        viewModel.refreshData();
        viewModel.getScanData().observe(requireActivity(), model -> {
            if(model.isEmpty()){
                dataBinding.frStockEmptyData.setVisibility(View.VISIBLE);
                dataBinding.frStockList.setVisibility(View.GONE);
            }else {
                dataBinding.frStockEmptyData.setVisibility(View.GONE);
                dataBinding.frStockList.setVisibility(View.VISIBLE);
               // countingAdapter.clearList();
                countingAdapter.addList(model, viewModel.getDocStatus());
               // setNotifCount(viewModel.getScannedCount()+"/"+model.size());
                countingAdapter.notifyDataSetChanged();
            }
        });


        viewModel.refreshHeaderData();
        viewModel.getHeaderData().observe(requireActivity(), model -> {
            if(!model.isEmpty()){
                AssetHeader head = model.get(0);
                locationCode = head.getBinCode();
                dataBinding.etLocation.setText(locationCode);
                if(model.get(0).getStatus().equalsIgnoreCase("C")){
                    dataBinding.layScan.setEnabled(false);
                    dataBinding.layAdd.setEnabled(false);
                    dataBinding.layAdd.setText("Done");
                    dataBinding.layCancel.setEnabled(false);
                    dataBinding.layoutScan.setVisibility(View.GONE);
                    Log.d("TAG", model.get(0).getStatus());
                }else if(model.get(0).getStatus().equalsIgnoreCase("P")){
                    dataBinding.layScan.setEnabled(true);
                    dataBinding.layAdd.setEnabled(true);
                    dataBinding.layAdd.setText("Update");
                    dataBinding.layCancel.setEnabled(true);
                    dataBinding.layoutScan.setVisibility(View.VISIBLE);
                } else{
                    dataBinding.layScan.setEnabled(true);
                    dataBinding.layAdd.setEnabled(true);
                    dataBinding.layAdd.setText("Add");
                    dataBinding.layCancel.setEnabled(true);
                    dataBinding.layoutScan.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void postToServer(String stRemarks, String stLocation, String stDocDate, String DocStatus) {
        viewModel.postDataToServer(stRemarks,stLocation,stDocDate,DocStatus);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_cart);
        View actionView = item.getActionView();
        TextView notificationCount = actionView.findViewById(R.id.cart_badge);
        /*if(item!=null)
            item.setVisible(false);*/
        viewModel.getItemCounts().observe(this, integer -> {
            String text = String.valueOf(viewModel.getDatabase().assetCountLineDao().getScannedDataSize());
            notificationCount.setText(String.valueOf(text+"/"+integer));
        });
    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 123) {
                        String args = result.getData().getStringExtra("ScannedData");
                        dataBinding.etLocation.setText(args);
                        locationCode = args;
                        Log.e("TAG_AssetTag", args);
                    } else if (result.getResultCode() == 124) {
                        //viewModel.refreshData();
                        viewModel.getDatabase().assetCountScanLineDao().deleteAllData();
                    }
                }
            }
    );



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Asset Tag");
        }
    }

    @Override
    public void onPostSuccess(String msg) {

        dataBinding.layScan.setEnabled(true);
        dataBinding.layAdd.setEnabled(true);
        dataBinding.layCancel.setEnabled(true);
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
        dialog.setTitle("Alert.");
        dialog.setMessage(""+msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Okay", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.show();

    }

    @Override
    public void onGetSuccess(String msg) {
        showSnackBar(msg);
    }

    @Override
    public void onGetData(List<GetAssetDocumentModel.ResponseObject> responseObject, String location, String DocStatus) {
        new AlertDialog.Builder(requireActivity())
                .setMessage("Selected location pending document exist! Click exist to continue pending document or Click New.")
                .setCancelable(false)
                .setPositiveButton("Exist", (dialog, id) -> {
                    viewModel.insertOpenData(responseObject, location, DocStatus);
                })
                .setNegativeButton("New", (dialog, which) -> {
                    viewModel.getDocument(String.valueOf(dataBinding.etLocation.getText()), "N");
                })
                .show();
    }

    @Override
    public void onPostFailed(String msg) {

        dataBinding.layScan.setEnabled(true);
        dataBinding.layAdd.setEnabled(true);
        dataBinding.layCancel.setEnabled(true);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Alert.");
        dialog.setMessage(""+msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Okay", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onGetFailed(String msg) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Alert");
        dialog.setMessage(""+msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Okay", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onPCustomerClick(int position, AssetCountLine models) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alart_qty_update_layout, null);
        TextView text = (TextView) customLayout.findViewById(R.id.alert_tvSysQty);
        EditText etText = (EditText) customLayout.findViewById(R.id.alert_etAccQty);
        text.setText(String.valueOf(models.getSysQty()));
        etText.setText(String.valueOf(models.getActQty()));
        builder.setView(customLayout);
        builder.setTitle("Update Actual Quantity.");
        builder.setMessage(models.getItemName()+"\n"+models.getItemCode()).setCancelable(false)
                .setPositiveButton("Update", (dialog, id) -> {
                    if(String.valueOf(etText.getText()).isEmpty()){
                        Toast.makeText(getActivity(), "Accepted quantity does not empty.", Toast.LENGTH_SHORT).show();
                    }else {
                        Float accQty = Float.valueOf(etText.getText().toString());
                        viewModel.getDatabase().assetCountLineDao().updateBatchInfo(models.getSlno(), accQty, "");
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
