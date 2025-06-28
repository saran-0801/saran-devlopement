package com.covalsys.ttss_barcode.ui.outward;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.models.post.PostInwardPallet;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.utils.DateUtils;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OutwardViewModel extends BaseViewModel<OutwardNavigator> {

    public static final String TAG = OutwardViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;

    private List<OutwardMaster> observeCustomerList = new ArrayList<>();
    public MediatorLiveData<List<OutwardMaster>> palletModel = new MediatorLiveData<>();

    @Inject
    public OutwardViewModel(Repository repository, SchedulerProvider schedulerProvider,
                            PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        progressBar = new MutableLiveData<>();
        palletChanges();
    }

    private void palletChanges() {
        LiveData<List<OutwardMaster>> palletLiveData = getDatabase().outwardMasterDao().getData();
        palletModel.addSource(palletLiveData, pallets -> {
            palletModel.setValue(pallets);
        });
    }

    public MediatorLiveData<List<OutwardMaster>> getPallet() {
        return palletModel;
    }

    public void updateOutCheckBoxStatus(Integer slno){
        getDatabase().outwardMasterDao().updateCheckBoxFlag(slno);
        getDatabase().outPalletLocationDao().updateCheckBoxFlag(slno);
        getDatabase().outPalletDao().updateCheckBoxFlag(slno);
    }

    public void postPalletData(){

        ArrayList<PostInwardPallet.PostInwardPalletItem> list = new ArrayList<>();
        List<OutwardMaster> listLiveData = getDatabase().outwardMasterDao().postDataToServer();
            for (OutwardMaster item : listLiveData) {
                list.add(new PostInwardPallet.PostInwardPalletItem(item.getPalletCode(), item.getPalletLocationCode(), getPreferenceHelper().getSalesEmpCode(), DateUtils.currentDateTimeYYYY()));
            }

            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService().addOutward(getPreferenceHelper().getUserType(), list)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(basePostResponse -> {
                        progressBar.setValue(false);
                        if (basePostResponse.getStatusCode() == 0) {
                            getNavigator().onPostSuccess(basePostResponse.getResponseObject());
                            getDatabase().outPalletDao().deleteAllData();
                            getDatabase().outPalletLocationDao().deleteAllData();
                            getDatabase().outwardMasterDao().deleteAllData();
                        } else {
                            getNavigator().onPostFailed(basePostResponse.getResponseObject());
                        }
                    }, throwable -> {
                        progressBar.setValue(false);
                        getNavigator().onPostFailed(throwable.getMessage());

                    }));

    }

    public void doInDelete() {
        getDatabase().outwardMasterDao().deleteData();
        getDatabase().outPalletLocationDao().deleteData();
        getDatabase().outPalletDao().deleteData();
    }
}