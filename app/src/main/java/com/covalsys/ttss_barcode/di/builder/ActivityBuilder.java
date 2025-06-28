package com.covalsys.ttss_barcode.di.builder;

import com.covalsys.ttss_barcode.ui.gate.GateActivity;
import com.covalsys.ttss_barcode.ui.gate.GateListActivity;
import com.covalsys.ttss_barcode.ui.gate.activity.BatchScannerActivity;
import com.covalsys.ttss_barcode.ui.login.LoginActivity;
import com.covalsys.ttss_barcode.ui.main.MainActivity;
import com.covalsys.ttss_barcode.ui.splash.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SplashScreenActivity splashScreenActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector
    abstract GateActivity gateActivity();

    @ContributesAndroidInjector
    abstract GateListActivity gateListActivity();

    @ContributesAndroidInjector
    abstract BatchScannerActivity batchScannerActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity mainActivity();


}
