package com.covalsys.ttss_barcode.di.builder;

import com.covalsys.ttss_barcode.ui.asset_tag.AssetCountingFragment;
import com.covalsys.ttss_barcode.ui.home.HomeFragment;
import com.covalsys.ttss_barcode.ui.inward.InwardFragment;
import com.covalsys.ttss_barcode.ui.outward.OutwardFragment;
import com.covalsys.ttss_barcode.ui.stock_counting.StockCountingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentBuilder {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract InwardFragment contributeInwardFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract OutwardFragment contributeOutwardFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract StockCountingFragment contributeStockCountingFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract AssetCountingFragment contributeAssetCountingFragment();

}
