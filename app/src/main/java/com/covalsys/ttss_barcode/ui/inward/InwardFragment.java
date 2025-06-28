package com.covalsys.ttss_barcode.ui.inward;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.Pallet;
import com.covalsys.ttss_barcode.data.database.entities.PalletLocation;
import com.covalsys.ttss_barcode.databinding.FragmentInwardBinding;
import com.covalsys.ttss_barcode.ui.base.BaseFragment;
import com.covalsys.ttss_barcode.ui.scan.ScanActivity;
import com.covalsys.ttss_barcode.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InwardFragment extends BaseFragment<InwardViewModel, FragmentInwardBinding> implements InwardNavigator,
        InwardScannedDataAdapter.Callback {

    public static final String TAG = InwardFragment.class.getSimpleName();
    private boolean inPallet = true;
    Context _context;
    BroadcastReceiver mReceiver;
    //private List<InwardMaster> inwardList = new ArrayList<>();
    @Inject
    InwardScannedDataAdapter mAdapter;

    @Override
    public int getBindingVariable() {
        return BR.inwardViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_inward;
    }

    @Override
    protected Class<InwardViewModel> getViewModel() {
        return InwardViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= 34 && Objects.requireNonNull(getActivity()).getApplicationInfo().targetSdkVersion >= 34) {
            return super.registerReceiver(mReceiver, filter, Context.RECEIVER_EXPORTED);
        } else {
            return super.registerReceiver(mReceiver, filter);
        }*/
        setHasOptionsMenu(true);
        viewModel.setNavigator(this);
        mAdapter.setOnClick(this);
        _context = requireActivity();
        setUp();
        return dataBinding.getRoot();
    }

    private void setUp() {

        LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.inwardRecyclerView.setLayoutManager(primaryManager);
        dataBinding.inwardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.inwardRecyclerView.setAdapter(mAdapter);
        dataBinding.multiCheckBox.setChecked(false);

        dataBinding.layInPalletInfo.setOnClickListener(v -> {

            inPallet = true;
               dataBinding.layInPalletInfo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            dataBinding.layInPalletLocationInfo.setBackgroundColor(getResources().getColor(R.color.gray_dark));

        });

        dataBinding.layInPalletLocationInfo.setOnClickListener(v -> {

            inPallet = false;
            dataBinding.layInPalletInfo.setBackgroundColor(getResources().getColor(R.color.gray_dark));
            dataBinding.layInPalletLocationInfo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        });

        dataBinding.layScan.setOnClickListener(v -> {

            activityResultLaunch.launch(new Intent(getActivity(), ScanActivity.class).putExtra("Inward", 1));

        });

        customersObservables();
    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 123) {
                        if(inPallet){
                            if (viewModel.getDatabase().palletDao().isAddToCart(result.getData().getStringExtra("ScannedData")) != 1) {
                                viewModel.getDatabase().palletDao().add(new Pallet(result.getData().getStringExtra("ScannedData"), "0"));
                                if(viewModel.getDatabase().palletDao().tableExistNo() == 1){
                                    viewModel.getDatabase().inwardMasterDao().updatePallet(result.getData().getStringExtra("ScannedData"), viewModel.getDatabase().palletDao().updatedLocationValue());
                                }else{
                                    viewModel.getDatabase().inwardMasterDao().add(new InwardMaster(result.getData().getStringExtra("ScannedData"), "", "0", DateUtils.currentDateNo(), false));
                                }

                                if(dataBinding.multiCheckBox.isChecked()){
                                    Log.e("TAG", "multiple checkbox enabled true...");
                                }else {
                                    dataBinding.layInPalletLocationInfo.callOnClick();
                                }

                            }else{
                                    showSnackBar("Scanned QR Code Already Exists In Pallet ");
                            }
                        }else {
                            //if (viewModel.getDatabase().palletLocationDao().isAddToCart(result.getData().getStringExtra("ScannedData")) != 1) {

                                if(dataBinding.multiCheckBox.isChecked()){
                                    int co = viewModel.getDatabase().inwardMasterDao().countPalletLocationEmpty();
                                    for(int i=1;i<=co;i++){
                                        viewModel.getDatabase().palletLocationDao().add(new PalletLocation(result.getData().getStringExtra("ScannedData"), "0"));
                                    }
                                    viewModel.getDatabase().inwardMasterDao().updatePalletLocationEmpty(result.getData().getStringExtra("ScannedData"));

                                    Log.e("TAG", "multiple checkbox enabled true...");
                                }else {
                                    viewModel.getDatabase().palletLocationDao().add(new PalletLocation(result.getData().getStringExtra("ScannedData"), "0"));
                                    if(viewModel.getDatabase().palletLocationDao().tableExistNo() == 1){
                                        viewModel.getDatabase().inwardMasterDao().updatePalletLocation(result.getData().getStringExtra("ScannedData"), viewModel.getDatabase().palletLocationDao().updatedLocationValue());
                                    }else{
                                        viewModel.getDatabase().inwardMasterDao().add(new InwardMaster("", result.getData().getStringExtra("ScannedData"), "0", DateUtils.currentDateNo(), false));
                                    }

                                    dataBinding.layInPalletInfo.callOnClick();
                                }

                            /*}else{
                                showSnackBar("Scanned QR Code Already Exists In Pallet Location ");
                            }*/
                        }
                    }
                }
            });

    public void customersObservables() {
        viewModel.progressBar.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        viewModel.getPallet().observe(getViewLifecycleOwner(), pallets -> {
            mAdapter.clearList();
            mAdapter.addCustomers(pallets);
            mAdapter.notifyDataSetChanged();
            dataBinding.inwardRecyclerView.scrollToPosition(pallets.size()-1);
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onPause() {
        super.onPause();
        _context.unregisterReceiver(mReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Inward");
        }

        InnerBroadcastReceiver();
    }

    public void InnerBroadcastReceiver(){

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String barcode = intent.getStringExtra("SCAN_BARCODE1");
                    int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1);
                    Log.e("TAG", barcodeType+"");
                    if (barcode != null) {
                        if(inPallet){
                            if (viewModel.getDatabase().palletDao().isAddToCart(barcode) != 1) {
                                viewModel.getDatabase().palletDao().add(new Pallet(barcode, "0"));
                                if(viewModel.getDatabase().palletDao().tableExistNo() == 1){
                                    viewModel.getDatabase().inwardMasterDao().updatePallet(barcode, viewModel.getDatabase().palletDao().updatedLocationValue());
                                }else{
                                    viewModel.getDatabase().inwardMasterDao().add(new InwardMaster(barcode, "", "0", DateUtils.currentDateNo(), false));
                                }

                                if(dataBinding.multiCheckBox.isChecked()){
                                    Log.e("TAG", "multiple checkbox enabled true...");
                                }else {
                                    dataBinding.layInPalletLocationInfo.callOnClick();
                                }

                            }else{
                                showSnackBar("Scanned QR Code Already Exists In Pallet ");
                            }
                        }else {
                            //if (viewModel.getDatabase().palletLocationDao().isAddToCart(barcode) != 1) {
                            if(dataBinding.multiCheckBox.isChecked()){
                                int co = viewModel.getDatabase().inwardMasterDao().countPalletLocationEmpty();
                                for(int i=1;i<=co;i++){
                                    viewModel.getDatabase().palletLocationDao().add(new PalletLocation(barcode, "0"));
                                }
                                viewModel.getDatabase().inwardMasterDao().updatePalletLocationEmpty(barcode);  //, viewModel.getDatabase().palletLocationDao().updatedLocationValue()

                                Log.e("TAG", "multiple checkbox enabled true...");
                            }else {
                                viewModel.getDatabase().palletLocationDao().add(new PalletLocation(barcode, "0"));
                                if (viewModel.getDatabase().palletLocationDao().tableExistNo() == 1) {
                                    viewModel.getDatabase().inwardMasterDao().updatePalletLocation(barcode, viewModel.getDatabase().palletLocationDao().updatedLocationValue());
                                } else {
                                    viewModel.getDatabase().inwardMasterDao().add(new InwardMaster("", barcode, "0", DateUtils.currentDateNo(), false));
                                }
                                dataBinding.layInPalletInfo.callOnClick();
                            }
                            /*}else{
                                showSnackBar("Scanned QR Code Already Exists In Pallet Location ");
                            }*/
                        }
                    } else {
                        showSnackBar("Barcode Not getting.");
                    }
                } else {
                    showSnackBar("Scan Failed");
                }
            }
        };


        if (Build.VERSION.SDK_INT >= 34 && requireActivity().getApplicationInfo().targetSdkVersion >= 34) {
            _context.registerReceiver(mReceiver, new IntentFilter("nlscan.action.SCANNER_RESULT"), Context.RECEIVER_EXPORTED);
            //return super.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
        } else {
            _context.registerReceiver(mReceiver, new IntentFilter("nlscan.action.SCANNER_RESULT"));
            //return super.registerReceiver(receiver, filter);
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.action_upload);
        MenuItem item1 = menu.findItem(R.id.action_delete);
        if (item != null)
            item.setVisible(true);
        if (item1 != null)
            item1.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_upload) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Click Yes to post sever!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        postToServer();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        }else if (item.getItemId() == R.id.action_delete) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Are you sure want to remove selected row!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        doCancel();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void postToServer(){

        if(viewModel.getDatabase().palletDao().getCount() != viewModel.getDatabase().palletLocationDao().getCount()){
            showSnackBar("Please Check Pallet and Pallet location!");
        }else{
            viewModel.postPalletData();
        }
    }

    public void doCancel(){
        List<InwardMaster> inwardList = new ArrayList<>();
        inwardList = mAdapter.getInwardList();
        for(InwardMaster in : inwardList){
            if(in.getChBox()) {
                viewModel.getDatabase().inwardMasterDao().deleteData(in.getSlno());
                viewModel.getDatabase().palletDao().deleteData(in.getSlno());
                viewModel.getDatabase().palletLocationDao().deleteData(in.getSlno());
            }
        }
    }

    @Override
    public void onPostSuccess(String msg) {
        showSnackBar(msg);
    }

    @Override
    public void onPostFailed(String msg) {
        showSnackBar(msg);
    }

    @Override
    public void onInCheckBoxUpdate(int position, InwardMaster models, boolean flag) {

    }
}
