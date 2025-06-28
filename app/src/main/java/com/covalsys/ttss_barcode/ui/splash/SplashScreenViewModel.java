package com.covalsys.ttss_barcode.ui.splash;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import javax.inject.Inject;

/**
 * Created by CBS on 21-08-2020.
 */
public class SplashScreenViewModel extends BaseViewModel<SplashScreenNavigator> {

    @Inject
    public SplashScreenViewModel(Repository repository, SchedulerProvider schedulerProvider,
                                 PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
    }



}
