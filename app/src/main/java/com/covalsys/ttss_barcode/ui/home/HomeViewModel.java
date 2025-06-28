package com.covalsys.ttss_barcode.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public static final String TAG = HomeViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;

    @Inject
    public HomeViewModel(Repository repository, SchedulerProvider schedulerProvider,
                         PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        progressBar = new MutableLiveData<>();
    }
}