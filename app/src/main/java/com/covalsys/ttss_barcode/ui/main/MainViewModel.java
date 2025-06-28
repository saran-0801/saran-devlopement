package com.covalsys.ttss_barcode.ui.main;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
import com.covalsys.ttss_barcode.data.database.entities.PalletLocation;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.models.post.PostInwardPallet;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.ui.home.HomeNavigator;
import com.covalsys.ttss_barcode.utils.DateUtils;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by CBS on 09-07-2020.
 */
public class MainViewModel extends BaseViewModel<HomeNavigator> {

    private static final String TAG = "MainViewModel";

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    private final ObservableField<String> appVersion = new ObservableField<>();

    private final ObservableField<String> userEmail = new ObservableField<>();

    private final ObservableField<String> userName = new ObservableField<>();

    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();


    @Inject
    public MainViewModel(Repository repository, SchedulerProvider schedulerProvider,
                         PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
    }


    public String getUserName(){
        return getPreferenceHelper().getSalesEmpName();
    }

    public String getUserMobile(){
        return getPreferenceHelper().getMobileNo();
    }

    public String getUserType(){
        return getPreferenceHelper().getUserType();
    }

    public String getUserCode(){
        return getPreferenceHelper().getUserCode();
    }

    public void postAllData(){

        MediatorLiveData<List<PalletLocation>> locationModel = new MediatorLiveData<>();
        ArrayList<PostInwardPallet.PostInwardPalletItem> list = new ArrayList<>();
        LiveData<List<InwardMaster>> listLiveData = getDatabase().palletDao().getPostData();
        locationModel.addSource(listLiveData, locations -> {
            for (InwardMaster item : locations) {
                list.add(new PostInwardPallet.PostInwardPalletItem(item.getPalletCode(), item.getPalletLocationCode(), getPreferenceHelper().getSalesEmpCode(), DateUtils.currentDateTimeYYYY()));
            }

            getCompositeDisposable().add(getApiService().addInward(getPreferenceHelper().getEmpTypeCode(), list)
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(basePostResponse -> {

                                if (basePostResponse.getStatusCode() == 0) {

                                    getDatabase().palletDao().deleteAllData();
                                    getDatabase().palletLocationDao().deleteAllData();
                                } else {

                                }
                            }, throwable -> {

                            }));
            });



    }

    public void deleteLocal(){
        getPreferenceHelper().setIsLoggedIn(false);
        getDatabase().outPalletDao().deleteAllData();
        getDatabase().outPalletLocationDao().deleteAllData();
        getDatabase().outwardMasterDao().deleteAllData();
        getDatabase().palletDao().deleteAllData();
        getDatabase().palletLocationDao().deleteAllData();
        getDatabase().inwardMasterDao().deleteAllData();
    }
}

