package com.covalsys.ttss_barcode.ui.inward;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
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

public class InwardViewModel extends BaseViewModel<InwardNavigator> {

    public static final String TAG = InwardViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;

    private List<InwardMaster> observeCustomerList = new ArrayList<>();
    public MediatorLiveData<List<InwardMaster>> palletModel = new MediatorLiveData<>();

    @Inject
    public InwardViewModel(Repository repository, SchedulerProvider schedulerProvider,
                           PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        progressBar = new MutableLiveData<>();
        palletChanges();
    }

    private void palletChanges() {
        LiveData<List<InwardMaster>> palletLiveData = getDatabase().inwardMasterDao().getData();
        palletModel.addSource(palletLiveData, pallets -> {
            palletModel.setValue(pallets);
        });
    }

    public MediatorLiveData<List<InwardMaster>> getPallet() {
        return palletModel;
    }

    public void postPalletData(){

        ArrayList<PostInwardPallet.PostInwardPalletItem> list = new ArrayList<>();
        List<InwardMaster> listLiveData = getDatabase().palletDao().postDataToServer();
            for (InwardMaster item : listLiveData) {
                list.add(new PostInwardPallet.PostInwardPalletItem(item.getPalletCode(), item.getPalletLocationCode(), getPreferenceHelper().getSalesEmpCode(), DateUtils.currentDateTimeYYYY()));
            }

            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService().addInward(getPreferenceHelper().getUserType(), list)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(basePostResponse -> {
                        progressBar.setValue(false);
                        if (basePostResponse.getStatusCode() == 0) {
                            getNavigator().onPostSuccess(basePostResponse.getResponseObject());
                            getDatabase().inwardMasterDao().deleteAllData();
                            getDatabase().palletDao().deleteAllData();
                            getDatabase().palletLocationDao().deleteAllData();
                        } else {
                            getNavigator().onPostFailed(basePostResponse.getResponseObject());
                        }
                    }, throwable -> {
                        progressBar.setValue(false);
                        getNavigator().onPostFailed(throwable.getMessage());
                    }));

    }

    public void updateInCheckBoxStatus(Integer slno) {
        getDatabase().inwardMasterDao().updateCheckBoxFlag(slno);
        /*getDatabase().palletDao().updateCheckBoxFlag(slno);
        getDatabase().palletLocationDao().updateCheckBoxFlag(slno);*/
    }

    public void doInDelete() {
        getDatabase().inwardMasterDao().deleteSelectedAllData();
        /*getDatabase().palletDao().deleteSelectedAllData();
        getDatabase().palletLocationDao().deleteSelectedAllData();*/
    }
}