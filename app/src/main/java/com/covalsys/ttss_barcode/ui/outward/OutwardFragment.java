package com.covalsys.ttss_barcode.ui.outward;

import static com.covalsys.ttss_barcode.ui.main.MainActivity.getBarcodeObject;

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
import com.covalsys.ttss_barcode.data.database.entities.OutPallet;
import com.covalsys.ttss_barcode.data.database.entities.OutPalletLocation;
import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;
import com.covalsys.ttss_barcode.databinding.FragmentOutwardBinding;
import com.covalsys.ttss_barcode.ui.base.BaseFragment;
import com.covalsys.ttss_barcode.ui.scan.ScanActivity;
import com.covalsys.ttss_barcode.utils.DateUtils;
import com.honeywell.aidc.BarcodeReader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OutwardFragment extends BaseFragment<OutwardViewModel, FragmentOutwardBinding> implements OutwardNavigator,
        OutwardScannedDataAdapter.Callback {

    public static final String TAG = OutwardFragment.class.getSimpleName();
    private boolean pallet = true;
    private BarcodeReader barcodeReader;
    Context _context;
    BroadcastReceiver mReceiver;

    private static final String ACTION_BARCODE_DATA = "com.honeywell.sample.action.BARCODE_DATA";
    private static final String ACTION_CLAIM_SCANNER = "com.honeywell.aidc.action.ACTION_CLAIM_SCANNER";
    private static final String ACTION_RELEASE_SCANNER = "com.honeywell.aidc.action.ACTION_RELEASE_SCANNER";
    private static final String EXTRA_SCANNER = "com.honeywell.aidc.extra.EXTRA_SCANNER";
    private static final String EXTRA_PROFILE = "com.honeywell.aidc.extra.EXTRA_PROFILE";
    private static final String EXTRA_PROPERTIES = "com.honeywell.aidc.extra.EXTRA_PROPERTIES";

    @Inject
    OutwardScannedDataAdapter mAdapter;

    @Override
    public int getBindingVariable() {
        return BR.outwardViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_outward;
    }

    @Override
    protected Class<OutwardViewModel> getViewModel() {
        return OutwardViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel.setNavigator(this);
        mAdapter.setOnClick(this);
        _context = getActivity();
        setUp();
        return dataBinding.getRoot();
    }

    private void setUp() {

        LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBinding.recyclerView.setLayoutManager(primaryManager);
        dataBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.recyclerView.setAdapter(mAdapter);
        dataBinding.multiCheckBoxOut.setChecked(false);

        barcodeReader = getBarcodeObject();

        dataBinding.layPalletInfo.setOnClickListener(v -> {

            pallet = true;
            dataBinding.layPalletInfo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            dataBinding.layPalletLocationInfo.setBackgroundColor(getResources().getColor(R.color.gray_dark));

        });

        dataBinding.layPalletLocationInfo.setOnClickListener(v -> {

            pallet = false;
            dataBinding.layPalletInfo.setBackgroundColor(getResources().getColor(R.color.gray_dark));
            dataBinding.layPalletLocationInfo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        });

        dataBinding.layScan.setOnClickListener(v -> {

            activityResultLaunch.launch(new Intent(getActivity(), ScanActivity.class).putExtra("Outward", 1));

        });

        /*if (barcodeReader != null) {
            // register bar code event listener
            barcodeReader.addBarcodeListener(new BarcodeReader.BarcodeListener() {
                @Override
                public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
                    Log.e("Barcode data: " , barcodeReadEvent.getBarcodeData()+"");
                    showSnackBar(barcodeReadEvent.getBarcodeData()+"");

                }

                @Override
                public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

                }
            });
            // set the trigger mode to client control
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE, BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
            } catch (UnsupportedPropertyException e) {
                showSnackBar("Failed to apply properties");
            }
            // register trigger state change listener
            //barcodeReader.addTriggerListener(this);
            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Disable bad read response, handle in onFailureEvent
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, false);
            // Sets time period for decoder timeout in any mode
            properties.put(BarcodeReader.PROPERTY_DECODER_TIMEOUT,  400);
            try {
                properties.put(BarcodeReader.PROPERTY_TRIG_PRES_MODE, true);
                properties.put(BarcodeReader.PROPERTY_TRIG_PRES_AIMER_ON, true);
                properties.put(BarcodeReader.PROPERTY_TRIG_PRES_ILLUM_ON_TIME, 3000);
                properties.put(BarcodeReader.PROPERTY_TRIG_PRES_IDLE_ILLUM_ON, true);
                properties.put(BarcodeReader.PROPERTY_TRIG_PRES_IDLE_ILLUM_ON_INTENSITY, 50);
                barcodeReader.softwareTrigger(true);

            } catch (Exception exception) {
                Log.d("ClientBarcode", exception.getMessage());
            }
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED,  true);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_REDUNDANCY_MODE,  10);
            properties.put(BarcodeReader.PROPERTY_LINEAR_VOID_HANDLING,  false);

            // Apply the settings
            barcodeReader.setProperties(properties);
        }*/

        customersObservables();
    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 123) {
                        if(pallet){
                            if (viewModel.getDatabase().outPalletDao().isAddToCart(result.getData().getStringExtra("ScannedData")) != 1) {
                                viewModel.getDatabase().outPalletDao().add(new OutPallet(result.getData().getStringExtra("ScannedData"), "0"));
                                if(viewModel.getDatabase().outPalletDao().tableExistNo() == 1){
                                    viewModel.getDatabase().outwardMasterDao().updatePallet(result.getData().getStringExtra("ScannedData"), viewModel.getDatabase().outPalletDao().updatedLocationValue());
                                }else{
                                    viewModel.getDatabase().outwardMasterDao().add(new OutwardMaster(result.getData().getStringExtra("ScannedData"), "", "0", DateUtils.currentDateNo(), false));
                                }

                                if(dataBinding.multiCheckBoxOut.isChecked()){
                                    Log.e("TAG", "multiple checkbox enabled true...");
                                }else {
                                    dataBinding.layPalletLocationInfo.callOnClick();
                                }
                            }else{
                                    showSnackBar("Scanned QR Code Already Exists In Pallet ");
                            }
                        }else {
                            //if (viewModel.getDatabase().outPalletLocationDao().isAddToCart(result.getData().getStringExtra("ScannedData")) != 1) {
                            if(dataBinding.multiCheckBoxOut.isChecked()){
                                int co = viewModel.getDatabase().outwardMasterDao().countPalletLocationEmpty();
                                for(int i=1;i<=co;i++){
                                    viewModel.getDatabase().outPalletLocationDao().add(new OutPalletLocation(result.getData().getStringExtra("ScannedData"), "0"));
                                }
                                viewModel.getDatabase().outwardMasterDao().updatePalletLocationEmpty(result.getData().getStringExtra("ScannedData"));  //, viewModel.getDatabase().outPalletLocationDao().updatedLocationValue()

                            }else {
                                viewModel.getDatabase().outPalletLocationDao().add(new OutPalletLocation(result.getData().getStringExtra("ScannedData"), "0"));
                                if (viewModel.getDatabase().outPalletLocationDao().tableExistNo() == 1) {
                                    viewModel.getDatabase().outwardMasterDao().updatePalletLocation(result.getData().getStringExtra("ScannedData"), viewModel.getDatabase().outPalletLocationDao().updatedLocationValue());
                                } else {
                                    viewModel.getDatabase().outwardMasterDao().add(new OutwardMaster("", result.getData().getStringExtra("ScannedData"), "0", DateUtils.currentDateNo(), false));
                                }
                                dataBinding.layPalletInfo.callOnClick();
                            }
                            /*}else{
                                showSnackBar("Scanned QR Code Already Exists In Pallet Location ");
                            }*/
                        }
                    }
                }
            });


    @Override
    public void onPause() {
        super.onPause();
        _context.unregisterReceiver(mReceiver);
    }

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
            dataBinding.recyclerView.scrollToPosition(pallets.size()-1);
        });
    }

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
            actionBar.setTitle("Outward");
        }

        // Here Camera is automatically  screen navigation home screen -----
        /*_context.registerReceiver(mReceiver, new IntentFilter(ACTION_BARCODE_DATA));
        claimScanner();*/
        InnerBroadcastReceiver();
    }

    private void claimScanner() {
        Bundle properties = new Bundle();
        properties.putBoolean("DPR_DATA_INTENT", true);
        properties.putString("DPR_DATA_INTENT_ACTION", ACTION_BARCODE_DATA);
        _context.sendBroadcast(new Intent(ACTION_CLAIM_SCANNER)
                .setPackage("com.intermec.datacollectionservice")
                .putExtra(EXTRA_SCANNER, "dcs.scanner.imager")
                .putExtra(EXTRA_PROFILE, "MyProfile1")
                .putExtra(EXTRA_PROPERTIES, properties)
        );
    }
    private void releaseScanner() {
        _context.sendBroadcast(new Intent(ACTION_RELEASE_SCANNER)
                .setPackage("com.intermec.datacollectionservice")
        );
    }

    public void InnerBroadcastReceiver(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                /*if (ACTION_BARCODE_DATA.equals(intent.getAction())) {

                    int version = intent.getIntExtra("version", 0);
                    if (version >= 1) {
                        String aimId = intent.getStringExtra("aimId");
                        String charset = intent.getStringExtra("charset");
                        String codeId = intent.getStringExtra("codeId");
                        String data = intent.getStringExtra("data");
                        byte[] dataBytes = intent.getByteArrayExtra("dataBytes");
                        String dataBytesStr = "";//bytesToHexString(dataBytes);
                        String timestamp = intent.getStringExtra("timestamp");
                        String text = String.format(
                                "Data:%s\n" +
                                        "Charset:%s\n" +
                                        "Bytes:%s\n" +
                                        "AimId:%s\n" +
                                        "CodeId:%s\n" +
                                        "Timestamp:%s\n",
                                data, charset, dataBytesStr, aimId, codeId, timestamp);
                        //setText(text);
                        showSnackBar(" " + text);

                    }
                }*/

                if (intent != null) {
                    String barcode = intent.getStringExtra("SCAN_BARCODE1");
                    int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1);
                    Log.e("TAG", barcodeType + "");
                    if (barcode != null) {
                        if (pallet) {
                            if (viewModel.getDatabase().outPalletDao().isAddToCart(barcode) != 1) {
                                viewModel.getDatabase().outPalletDao().add(new OutPallet(barcode, "0"));
                                if (viewModel.getDatabase().outPalletDao().tableExistNo() == 1) {
                                    viewModel.getDatabase().outwardMasterDao().updatePallet(barcode, viewModel.getDatabase().outPalletDao().updatedLocationValue());
                                } else {
                                    viewModel.getDatabase().outwardMasterDao().add(new OutwardMaster(barcode, "", "0", DateUtils.currentDateNo(), false));
                                }

                                if(dataBinding.multiCheckBoxOut.isChecked()){
                                    Log.e("TAG", "multiple checkbox enabled true...");
                                }else {
                                    dataBinding.layPalletLocationInfo.callOnClick();
                                }
                            } else {
                                showSnackBar("Scanned QR Code Already Exists In Pallet ");
                            }
                        } else {
                            //if (viewModel.getDatabase().outPalletLocationDao().isAddToCart(barcode) != 1) {
                            if(dataBinding.multiCheckBoxOut.isChecked()){
                                int co = viewModel.getDatabase().outwardMasterDao().countPalletLocationEmpty();
                                for(int i=1;i<=co;i++){
                                    viewModel.getDatabase().outPalletLocationDao().add(new OutPalletLocation(barcode, "0"));
                                }
                                viewModel.getDatabase().outwardMasterDao().updatePalletLocationEmpty(barcode); //, viewModel.getDatabase().outPalletLocationDao().updatedLocationValue()

                            }else {
                                viewModel.getDatabase().outPalletLocationDao().add(new OutPalletLocation(barcode, "0"));
                                if (viewModel.getDatabase().outPalletLocationDao().tableExistNo() == 1) {
                                    viewModel.getDatabase().outwardMasterDao().updatePalletLocation(barcode, viewModel.getDatabase().outPalletLocationDao().updatedLocationValue());
                                } else {
                                    viewModel.getDatabase().outwardMasterDao().add(new OutwardMaster("", barcode, "0", DateUtils.currentDateNo(), false));
                                }
                                dataBinding.layPalletInfo.callOnClick();
                            }
                            /*} else {
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

        if(viewModel.getDatabase().outPalletDao().getCount() < viewModel.getDatabase().outPalletLocationDao().getCount()){
            showSnackBar("Please Check Pallet is missing!");
        }else{
            viewModel.postPalletData();
        }
    }

    public void doCancel(){

        List<OutwardMaster> outwardList = new ArrayList<>();
        outwardList = mAdapter.getOutwardList();
        for(OutwardMaster in : outwardList){
            if(in.getChBox()) {
                viewModel.getDatabase().outwardMasterDao().deleteData(in.getSlno());
                viewModel.getDatabase().outPalletDao().deleteData(in.getSlno());
                viewModel.getDatabase().outPalletLocationDao().deleteData(in.getSlno());
            }
        }

        viewModel.doInDelete();
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
    public void onOutCheckBoxUpdate(int position, OutwardMaster models, boolean flag) {
        //viewModel.updateOutCheckBoxStatus(models.getSlno());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
