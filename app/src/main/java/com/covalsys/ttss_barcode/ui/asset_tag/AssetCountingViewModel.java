package com.covalsys.ttss_barcode.ui.asset_tag;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.database.entities.AssetCountLine;
import com.covalsys.ttss_barcode.data.database.entities.AssetHeader;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.Resource;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.utils.DateUtils;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;
import com.covalsys.ttss_inv.data.network.models.get.GetAssetDocumentModel;
import com.covalsys.ttss_inv.data.network.models.get.GetAssetLocationModel;
import com.covalsys.ttss_inv.data.network.models.post.PostAssetTag;
import com.covalsys.ttss_inv.data.network.models.post.PostAssetTagModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AssetCountingViewModel extends BaseViewModel<AssetCountingNavigator> {

    public static final String TAG = AssetCountingViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;
    public MutableLiveData<Context> mContext;
    public MediatorLiveData<List<AssetCountLine>> model = new MediatorLiveData<>();
    public MediatorLiveData<List<AssetHeader>> hModel = new MediatorLiveData<>();
    public MutableLiveData<Resource<GetAssetLocationModel>> location;
    public MutableLiveData<Resource<PostAssetTagModel>> dDocLine;
    public MutableLiveData<String> nextDate;

    @Inject
    public AssetCountingViewModel(Repository repository, SchedulerProvider schedulerProvider, PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);
        progressBar = new MutableLiveData<>();
        mContext = new MutableLiveData<>();
        location = new MutableLiveData<>();
        dDocLine = new MutableLiveData<>();
        nextDate = new MutableLiveData<>();
        init();
    }

    public void init(){
        getCustomersDetails();
    }

    public void setContext(Context context) {
        mContext.setValue(context);
    }


    public void refreshData(){
       deliveryList();
    }

    private void deliveryList() {
        LiveData<List<AssetCountLine>> listLiveData = getDatabase().assetCountLineDao().getData();
        model.addSource(listLiveData, pallets -> {
            model.setValue(pallets);
        });
    }

    public void refreshHeaderData(){
        LiveData<List<AssetHeader>> listLiveData = getDatabase().assetHeaderDao().getData();
        hModel.addSource(listLiveData, assetHeaders -> {
            hModel.setValue(assetHeaders);
        });
    }

    public MediatorLiveData<List<AssetCountLine>> getScanData() {
        return model;
    }

    public MediatorLiveData<List<AssetHeader>> getHeaderData() {
        return hModel;
    }

    public LiveData<Integer> getItemCounts() {
        return getDatabase().assetCountLineDao().getRowCount();
    }

    public String getDocStatus() {
        return getDatabase().assetHeaderDao().getStatus();
    }

    public LiveData<Resource<GetAssetLocationModel>> getLocation() {
        return location;
    }

    public void getDocument(String location,String status){
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getAssetItemDetails(location,status)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getAssetDocumentModel -> {
                    progressBar.setValue(false);
                    if (getAssetDocumentModel.getStatusCode() == 0) {
                        if(getAssetDocumentModel.getDocStatus().equalsIgnoreCase("P")){
                            getNavigator().onGetData(getAssetDocumentModel.getResponseObject(), location, getAssetDocumentModel.getDocStatus());
                        }else{
                            insertOpenData(getAssetDocumentModel.getResponseObject(), location, getAssetDocumentModel.getDocStatus());
                        }
                        /*if(getAssetDocumentModel.getDocStatus().equalsIgnoreCase("O")) {
                            insertOpenData(getAssetDocumentModel.getResponseObject(), location);
                        }else{
                            insertClosedData(getAssetDocumentModel.getResponseObject(), location);
                        }
                        getNavigator().onGetSuccess(getAssetDocumentModel.getStatusMessage());*/
                    } else {
                        Log.e("TAG_B", getAssetDocumentModel.getStatusMessage());
                        getDatabase().assetCountLineDao().deleteAllData();
                        getDatabase().assetHeaderDao().deleteAllData();
                        getNavigator().onGetFailed(getAssetDocumentModel.getStatusMessage());
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    Log.e("TAG_B", throwable.toString());
                    getDatabase().assetCountLineDao().deleteAllData();
                    getDatabase().assetHeaderDao().deleteAllData();
                    getNavigator().onPostFailed("No data found.");
                }));
    }

    void insertOpenData(List<GetAssetDocumentModel.ResponseObject> value, String bin, String DocStatus) {
        getDatabase().assetCountLineDao().deleteAllData();
        getDatabase().assetHeaderDao().deleteAllData();
        int docNum = 0;
        for(GetAssetDocumentModel.ResponseObject ob: value){
            getDatabase().assetCountLineDao().add(new AssetCountLine(ob.getLine(),ob.getDocNum(), "", (float) ob.getSysQty(), (float) ob.getActQty(), ob.getItemCode(), ob.getItemName(), "", ob.getScanDate(), ob.getStatus()));
            docNum = ob.getDocNum();
        }
        getDatabase().assetHeaderDao().add(new AssetHeader(docNum, bin, "", "", DocStatus));
        refreshData();
        refreshHeaderData();
    }

    /*private void insertClosedData(List<GetAssetDocumentModel.ResponseObject> value, String bin) {
        getDatabase().assetCountLineDao().deleteAllData();
        getDatabase().assetHeaderDao().deleteAllData();
        int docNum = 0;
        for(GetAssetDocumentModel.ResponseObject ob: value){
            getDatabase().assetCountLineDao().add(new AssetCountLine(ob.getLine(),ob.getDocNum(), "", (float) ob.getSysQty(), (float) ob.getActQty(), ob.getItemCode(), ob.getItemName(), "", ob.getScanDate(), "A"));
            docNum = ob.getDocNum();
        }
        getDatabase().assetHeaderDao().add(new AssetHeader(docNum, bin, "", "", "C"));
        refreshData();
        refreshHeaderData();
    }*/

    private void getCustomersDetails() {
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getAssetLocationModel()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getAssetLocationModel -> {
                    progressBar.setValue(false);
                    if (getAssetLocationModel.getStatusCode() == 0) {
                        location.postValue(Resource.success(getAssetLocationModel));
                    } else {
                        location.postValue(Resource.error(getAssetLocationModel.getStatusMessage(), null));
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    location.postValue(Resource.error(throwable.toString(), null));
                }));
    }

    public LiveData<String> nextDate() {
        return nextDate;
    }

    public void postDataToServer(String stRemarks, String stLocation, String stDocDate, String DocStatus){

        progressBar.setValue(true);

        List<PostAssetTag.Line> CList = new ArrayList<>();
        List<AssetCountLine> listData = getDatabase().assetCountLineDao().getDataList();
        for (AssetCountLine item : listData) {
            CList.add(new PostAssetTag.Line(item.getActQty(), String.valueOf(item.getDocNum()), item.getItemCode(), item.getItemName(), String.valueOf(item.getDocEntry()), DateUtils.convertDateFormat13(item.getScanDate()), item.getStatus(), item.getSysQty()));
        }


        AssetHeader Data = getDatabase().assetHeaderDao().getSingleData();
        if (Data.getStatus().equalsIgnoreCase("O")) {
            PostAssetTag count = new PostAssetTag(DateUtils.currentDateYYYY(), DateUtils.currentDateYYYY(), CList, stLocation, getPreferenceHelper().getSalesEmpCode(), String.valueOf(Data.getDocNum()), DocStatus, DateUtils.currentDateYYYY());

            getCompositeDisposable().add(getApiService()
                    .addAssetTag(count)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(basePostResponse -> {
                        progressBar.setValue(false);
                        if (basePostResponse.getStatusCode() == 0) {
                            getDatabase().assetCountLineDao().deleteAllData();
                            getDatabase().assetHeaderDao().deleteAllData();
                            getDatabase().assetCountScanLineDao().deleteAllData();
                            getNavigator().onPostSuccess(basePostResponse.getResponseObject());
                        } else {
                            getNavigator().onPostFailed(basePostResponse.getResponseObject());
                        }
                    }, throwable -> {
                        progressBar.setValue(false);
                        getNavigator().onPostFailed(throwable.getMessage());
                    }));
        }else{
            PostAssetTag count = new PostAssetTag(DateUtils.currentDateYYYY(), DateUtils.currentDateYYYY(), CList, stLocation, getPreferenceHelper().getSalesEmpCode(), String.valueOf(Data.getDocNum()), DocStatus, DateUtils.currentDateYYYY());

            getCompositeDisposable().add(getApiService()
                    .updateAssetTag(count)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(basePostResponse -> {
                        progressBar.setValue(false);
                        if (basePostResponse.getStatusCode() == 0) {
                            getDatabase().assetCountLineDao().deleteAllData();
                            getDatabase().assetHeaderDao().deleteAllData();
                            getDatabase().assetCountScanLineDao().deleteAllData();
                            getNavigator().onPostSuccess(basePostResponse.getResponseObject());
                        } else {
                            getNavigator().onPostFailed(basePostResponse.getResponseObject());
                        }
                    }, throwable -> {
                        progressBar.setValue(false);
                        getNavigator().onPostFailed(throwable.getMessage());
                    }));
        }
    }
}
