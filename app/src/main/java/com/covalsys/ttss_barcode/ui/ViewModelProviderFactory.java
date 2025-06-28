package com.covalsys.ttss_barcode.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.asset_tag.AssetCountingViewModel;
import com.covalsys.ttss_barcode.ui.gate.GateViewModel;
import com.covalsys.ttss_barcode.ui.home.HomeViewModel;
import com.covalsys.ttss_barcode.ui.inward.InwardViewModel;
import com.covalsys.ttss_barcode.ui.login.LoginViewModel;
import com.covalsys.ttss_barcode.ui.main.MainViewModel;
import com.covalsys.ttss_barcode.ui.outward.OutwardViewModel;
import com.covalsys.ttss_barcode.ui.splash.SplashScreenViewModel;
import com.covalsys.ttss_barcode.ui.stock_counting.StockCountingViewModel;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;
    private final PreferenceHelper mPreferenceHelper;
    private final AppDatabase mDatabase;
    private final RoomHelper mHelper;
    private final ApiService mApiService;
    private final SchedulerProvider mSchedulerProvider;

    @Inject
    public ViewModelProviderFactory(Repository repository, SchedulerProvider schedulerProvider,
                                    PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        this.mRepository = repository;
        this.mPreferenceHelper = preferenceHelper;
        this.mDatabase = database;
        this.mHelper = helper;
        this.mApiService = service;
        this.mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)){
            //noinspection unchecked
            return (T) new HomeViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }else if (modelClass.isAssignableFrom(LoginViewModel.class)){
            //noinspection unchecked
            return (T) new LoginViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }else if (modelClass.isAssignableFrom(MainViewModel.class)){
            //noinspection unchecked
            return (T) new MainViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(SplashScreenViewModel.class)){
            //noinspection unchecked
            return (T) new SplashScreenViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(InwardViewModel.class)){
            //noinspection unchecked
            return (T) new InwardViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(OutwardViewModel.class)){
            //noinspection unchecked
            return (T) new OutwardViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(StockCountingViewModel.class)){
            //noinspection unchecked
            return (T) new StockCountingViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(GateViewModel.class)){
            //noinspection unchecked
            return (T) new GateViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        } else if (modelClass.isAssignableFrom(AssetCountingViewModel.class)){
            //noinspection unchecked
            return (T) new AssetCountingViewModel(mRepository,mSchedulerProvider,mPreferenceHelper,mDatabase,mHelper,mApiService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
