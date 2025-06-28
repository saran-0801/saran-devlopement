package com.covalsys.ttss_barcode.ui.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    private final Repository mRepository;

    private final ApiService apiService;

    private final AppDatabase mDatabase;

    private final RoomHelper helper;

    private final PreferenceHelper mPreferenceHelper;

    private final ObservableBoolean mIsLoading = new ObservableBoolean();

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    public BaseViewModel(Repository repository,SchedulerProvider schedulerProvider,
                         PreferenceHelper preferenceHelper,AppDatabase database, RoomHelper helper,ApiService service) {
        this.mRepository = repository;
        this.mSchedulerProvider = schedulerProvider;
        this.mPreferenceHelper = preferenceHelper;
        this.mDatabase = database;
        this.helper = helper;
        this.mCompositeDisposable = new CompositeDisposable();
        this.apiService = service;
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public PreferenceHelper getPreferenceHelper(){
        return mPreferenceHelper;
    }

    public AppDatabase getDatabase(){
        return mDatabase;
    }

    public RoomHelper getHelper(){
        return helper;
    }

    public ApiService getApiService(){
        return apiService;
    }

    public Repository getRepository() {
        return mRepository;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

}
