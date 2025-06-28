package com.covalsys.ttss_barcode.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.ttss_barcode.di.ViewModelKey;
import com.covalsys.ttss_barcode.ui.ViewModelProviderFactory;
import com.covalsys.ttss_barcode.ui.asset_tag.AssetCountingViewModel;
import com.covalsys.ttss_barcode.ui.gate.GateViewModel;
import com.covalsys.ttss_barcode.ui.home.HomeViewModel;
import com.covalsys.ttss_barcode.ui.inward.InwardViewModel;
import com.covalsys.ttss_barcode.ui.login.LoginViewModel;
import com.covalsys.ttss_barcode.ui.main.MainViewModel;
import com.covalsys.ttss_barcode.ui.outward.OutwardViewModel;
import com.covalsys.ttss_barcode.ui.splash.SplashScreenViewModel;
import com.covalsys.ttss_barcode.ui.stock_counting.StockCountingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsSplashScreenViewModel(SplashScreenViewModel splashScreenViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(InwardViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsInwardViewModel(InwardViewModel inwardViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OutwardViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsOutwardViewModel(OutwardViewModel outwardViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GateViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsGateViewModel(GateViewModel gateViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StockCountingViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsStockCountingViewModel(StockCountingViewModel stockCountingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AssetCountingViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsAssetCountingViewModel(AssetCountingViewModel assetCountingViewModel);


    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelProviderFactory viewModelFactory);
}
