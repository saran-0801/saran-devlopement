package com.covalsys.ttss_barcode.ui.main;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.WorkInfo;

import com.covalsys.ttss_barcode.BR;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.databinding.ActivityMainBinding;
import com.covalsys.ttss_barcode.ui.ViewModelProviderFactory;
import com.covalsys.ttss_barcode.ui.base.BaseActivity;
import com.covalsys.ttss_barcode.ui.login.LoginActivity;
import com.covalsys.ttss_barcode.utils.NetworkUtils;
import com.google.android.material.navigation.NavigationView;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.InvalidScannerNameException;
import com.honeywell.aidc.ScannerUnavailableException;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final String TAG = "LocationUpdate";
    private TextView notificationCount, cartCount;

    private static BarcodeReader barcodeReader;
    private AidcManager manager;

    @Inject
    ViewModelProviderFactory factory;

    private MainViewModel mMainViewModel;

    @Override
    public int getBindingVariable() {
        return BR.mainViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        return mMainViewModel;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //NavigationView navigationView = findViewById(R.id.nav_view);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerLayout = navigationView.getHeaderView(0);
        //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView name = headerLayout.findViewById(R.id.name);
        TextView mobile = headerLayout.findViewById(R.id.mobile);
        TextView login = headerLayout.findViewById(R.id.loginName);
        name.setText(mMainViewModel.getUserName());

        //mobile.setText(mMainViewModel.getUserMobile());
        if(mMainViewModel.getUserType().equalsIgnoreCase("S")) {
            mobile.setText("Super User");
        }else{
            mobile.setText("User");
        }
        login.setText(mMainViewModel.getUserCode());

        navigationView.getMenu().findItem(R.id.nav_settings).setOnMenuItemClickListener(menuItem -> {
            if (drawer != null) {
                drawer.closeDrawer(GravityCompat.START);
            }
            //startActivity(SettingsActivity.getStartIntent(MainActivity.this));
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            if (drawer != null) {
                drawer.closeDrawer(GravityCompat.START);
            }
            logout();
            return true;
        });


        Menu menu = navigationView.getMenu();
        if(mMainViewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("SKID")){
            MenuItem out = menu.findItem(R.id.nav_check_out);
            out.setVisible(true);
            MenuItem in = menu.findItem(R.id.nav_check_in);
            in.setVisible(true);
            MenuItem spare = menu.findItem(R.id.nav_inv);
            spare.setVisible(false);
            MenuItem gate = menu.findItem(R.id.nav_gate);
            gate.setVisible(false);
            MenuItem asset = menu.findItem(R.id.nav_asset);
            asset.setVisible(false);
        } else if (mMainViewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("STORE")) {
            MenuItem out = menu.findItem(R.id.nav_check_out);
            out.setVisible(false);
            MenuItem in = menu.findItem(R.id.nav_check_in);
            in.setVisible(false);
            MenuItem spare = menu.findItem(R.id.nav_inv);
            spare.setVisible(true);
            MenuItem gate = menu.findItem(R.id.nav_gate);
            gate.setVisible(false);
            MenuItem asset = menu.findItem(R.id.nav_asset);
            asset.setVisible(false);
        } else if (mMainViewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("SECURITY")) {
            MenuItem out = menu.findItem(R.id.nav_check_out);
            out.setVisible(false);
            MenuItem in = menu.findItem(R.id.nav_check_in);
            in.setVisible(false);
            MenuItem spare = menu.findItem(R.id.nav_inv);
            spare.setVisible(false);
            MenuItem gate = menu.findItem(R.id.nav_gate);
            gate.setVisible(true);
            MenuItem asset = menu.findItem(R.id.nav_asset);
            asset.setVisible(false);
        }else if (mMainViewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("ASSET")) {
            MenuItem out = menu.findItem(R.id.nav_check_out);
            out.setVisible(false);
            MenuItem in = menu.findItem(R.id.nav_check_in);
            in.setVisible(false);
            MenuItem spare = menu.findItem(R.id.nav_inv);
            spare.setVisible(false);
            MenuItem gate = menu.findItem(R.id.nav_gate);
            gate.setVisible(false);
            MenuItem asset = menu.findItem(R.id.nav_asset);
            asset.setVisible(true);
        } else{
            MenuItem out = menu.findItem(R.id.nav_check_out);
            out.setVisible(true);
            MenuItem in = menu.findItem(R.id.nav_check_in);
            in.setVisible(true);
            MenuItem spare = menu.findItem(R.id.nav_inv);
            spare.setVisible(true);
            MenuItem gate = menu.findItem(R.id.nav_gate);
            gate.setVisible(true);
            MenuItem asset = menu.findItem(R.id.nav_asset);
            asset.setVisible(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(() -> {
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment);
            currentFragment.onResume();
        });

        boolean checkConnection = NetworkUtils.isWifiNetworkConnected(this);
        if(checkConnection) {
            //mMainViewModel.postAllData();
        }else{
            Log.e("TAG", "Network connection Issue");
        }

        // create the AidcManager providing a Context and a
        // CreatedCallback implementation.
        AidcManager.create(this, new AidcManager.CreatedCallback() {

            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                try{
                    barcodeReader = manager.createBarcodeReader();
                }
                catch (InvalidScannerNameException e){
                    Toast.makeText(MainActivity.this, "Invalid Scanner Name Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isWorkScheduled(List<WorkInfo> workInfos) {
        boolean running = false;
        if (workInfos == null || workInfos.size() == 0) return false;
        for (WorkInfo workStatus : workInfos) {
            running = workStatus.getState() == WorkInfo.State.RUNNING | workStatus.getState() == WorkInfo.State.ENQUEUED;
        }
        return running;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                //startActivity(CartActivity.newIntent(this));
                return true;
            case R.id.action_notification:
                //startActivity(NotificationActivity.newIntent(this));
                return true;
            case R.id.action_sync:
                //startActivity(ViewCreatedActivity.newIntent(this));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    mMainViewModel.deleteLocal();
                    startActivity(LoginActivity.newIntent(MainActivity.this));
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * All about permission
     */
    private boolean checkLocationPermission() {
        int result3 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        int result4 = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        return result3 == PackageManager.PERMISSION_GRANTED &&
                result4 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean coarseLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean fineLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (coarseLocation && fineLocation)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private long lastPressedTime;
    private static final int PERIOD = 2000;

    public static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (event.getDownTime() - lastPressedTime < PERIOD) {
                    finish();
                } else {
                    showSnackBar("Press back again to exit Covalsys app.");
                    lastPressedTime = event.getEventTime();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mMainViewModel.getApprovalDetails();
        String add =  "Covalsysoft Solutions Pvt Ltd";
        //getLocationFromAddress(add);
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                Log.e("TAG_1", e.getMessage()+"");
                //showSnackBar(e.printStackTrace()+"");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barcodeReader != null) {
            // close BarcodeReader to clean up resources.
            barcodeReader.close();
            barcodeReader = null;
        }

        if (manager != null) {
            // close AidcManager to disconnect from the scanner service.
            // once closed, the object can no longer be used.
            manager.close();
        }
    }
}
