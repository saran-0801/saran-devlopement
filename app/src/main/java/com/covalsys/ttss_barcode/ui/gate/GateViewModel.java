package com.covalsys.ttss_barcode.ui.gate;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.Resource;
import com.covalsys.ttss_barcode.data.network.models.BarcodeList.BarcodeListModel;
import com.covalsys.ttss_barcode.data.network.models.BarcodeList.ResultItem;
import com.covalsys.ttss_barcode.data.network.models.HeaderModel.HeaderBarcodeModel;
import com.covalsys.ttss_barcode.data.network.models.HeaderModel.Result;
import com.covalsys.ttss_barcode.data.network.models.PostHeader.PostHeaderModel;
import com.covalsys.ttss_barcode.data.network.models.PostList.PostListModel;
import com.covalsys.ttss_barcode.data.network.models.get.gate.AreaOfWorkModel;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GateViewModel extends BaseViewModel<GateNavigator> {

    public static final String TAG = "GateViewModel";
    public MutableLiveData<Boolean> progressBar;
    public MutableLiveData<Resource<AreaOfWorkModel>> areaOfWorkList;
    private MutableLiveData<Resource<HeaderBarcodeModel>> headerBarcodeModelLiveData;
    private MutableLiveData<Resource<BarcodeListModel>> barcodeListModelLiveData;
    private MutableLiveData<Resource<PostHeaderModel>> headerPostDataLiveData;
    private MutableLiveData<Resource<PostListModel>> listPostDataLiveData;
    private String userToken = "";

    @Inject
    public GateViewModel(Repository repository, SchedulerProvider schedulerProvider,
                         PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);

        areaOfWorkList = new MutableLiveData<>();
        headerBarcodeModelLiveData = new MutableLiveData<>();
        barcodeListModelLiveData = new MutableLiveData<>();
        headerPostDataLiveData = new MutableLiveData<>();
        listPostDataLiveData = new MutableLiveData<>();
        progressBar = new MutableLiveData<>();
        userToken = getPreferenceHelper().getAccessToken();

      //  fetchAreaOfWork();
    }

    public MutableLiveData<Resource<AreaOfWorkModel>> getAreaOfWorkLiveData() {
        return areaOfWorkList;
    }

    public MutableLiveData<Resource<HeaderBarcodeModel>> getHeaderBarCodeLiveData() {
        if (headerBarcodeModelLiveData == null) {
            headerBarcodeModelLiveData = new MutableLiveData<>();
        }
        //headerBarcodeModelLiveData = appRepository.getHeaderBarCodeLiveData();
        return headerBarcodeModelLiveData;
    }

    public MutableLiveData<Resource<PostHeaderModel>> getHeaderPostDataLiveData() {
        if (headerPostDataLiveData == null) {
            headerPostDataLiveData = new MutableLiveData<>();
        }
        //headerPostDataLiveData = appRepository.getHeaderPostDataLiveData();
        return headerPostDataLiveData;
    }

    public MutableLiveData<Resource<PostListModel>> getListPostDataLiveData() {
        if (listPostDataLiveData == null) {
            listPostDataLiveData = new MutableLiveData<>();
        }
        //listPostDataLiveData = appRepository.getListPostDataLiveData();
        return listPostDataLiveData;
    }

    public MutableLiveData<Resource<BarcodeListModel>> getBarCodeListLiveData() {
        if (barcodeListModelLiveData == null) {
            barcodeListModelLiveData = new MutableLiveData<>();
        }
        //barcodeListModelLiveData = appRepository.getBarCodeListLiveData();
        return barcodeListModelLiveData;
    }

    public void getHeaderBarCode(String docNum) {
        fetchHeaderBarCode(docNum);
    }

    public void getBarCodeList(String docEntry, Integer groupnum) {
        fetchBarCodeList(docEntry, groupnum);
    }

    public void sendHeaderData(Result body) {
        sendToHeaderData(body);
    }

    public void sendBarCodeList(List<ResultItem> resultItemList, Integer groupnum) {
        sendToBarCodeList(resultItemList, groupnum);
    }

    public void fetchAreaOfWork() {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService()
                    .getAreaOfWork(map)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(loginModel -> {
                        progressBar.setValue(false);
                        if (loginModel.isSuccess()) {
                            areaOfWorkList.postValue(Resource.success(loginModel));
                            Log.e("TAG01", areaOfWorkList.toString());
                        } else {
                            areaOfWorkList.postValue(Resource.error(loginModel.getMessage().getDescription(), null));
                        }

                    }, throwable -> {
                        progressBar.setValue(false);
                        Log.e(TAG, throwable.toString());
                        areaOfWorkList.postValue(Resource.error(throwable.toString(), null));
                    }));
    }

    public void fetchHeaderBarCode(String docNum) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService()
                    .getHeaderBarCode(map, docNum)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(loginModel -> {
                        progressBar.setValue(false);
                        if (loginModel.isIsSuccess()) {
                            headerBarcodeModelLiveData.postValue(Resource.success(loginModel));
                        } else {
                            headerBarcodeModelLiveData.postValue(Resource.error(loginModel.getMessage().getDescription(), null));
                        }

                    }, throwable -> {
                        progressBar.setValue(false);
                        Log.e(TAG, throwable.toString());
                        headerBarcodeModelLiveData.postValue(Resource.error(throwable.toString(), null));
                    }));

    }

    public void fetchBarCodeList(String docEntry, Integer groupnum) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getBarCodeList(map, docEntry, groupnum)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(loginModel -> {
                    progressBar.setValue(false);
                    if (loginModel.isIsSuccess()) {
                        barcodeListModelLiveData.postValue(Resource.success(loginModel));
                    } else {
                        barcodeListModelLiveData.postValue(Resource.error(loginModel.getMessage().getDescription(), null));
                    }

                }, throwable -> {
                    progressBar.setValue(false);
                    Log.e(TAG, throwable.toString());
                    barcodeListModelLiveData.postValue(Resource.error(throwable.toString(), null));
                }));


    }


    public void sendToHeaderData(Result body) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

        Log.e("TAG01", "01");
        Log.e("TAG02", ""+body);

            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService()
                    .sendHeaderData(map, body)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(loginModel -> {
                        progressBar.setValue(false);
                        if (loginModel.isIsSuccess()) {
                            headerPostDataLiveData.postValue(Resource.success(loginModel));
                        } else {
                            headerPostDataLiveData.postValue(Resource.error(loginModel.getMessage().getDescription(), null));
                        }

                    }, throwable -> {
                        progressBar.setValue(false);
                        Log.e(TAG, throwable.toString());
                        headerPostDataLiveData.postValue(Resource.error(throwable.toString(), null));
                    }));

    }

    public void sendToBarCodeList(List<ResultItem> resultItemList, Integer groupnum) {

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", userToken);

            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService()
                    .sendBarCodeList(map, resultItemList, groupnum)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(loginModel -> {
                        progressBar.setValue(false);
                        if (loginModel.isIsSuccess()) {
                            listPostDataLiveData.postValue(Resource.success(loginModel));
                        } else {
                            listPostDataLiveData.postValue(Resource.error(loginModel.getMessage().getDescription(), null));
                        }

                    }, throwable -> {
                        progressBar.setValue(false);
                        Log.e(TAG, throwable.toString());
                        listPostDataLiveData.postValue(Resource.error(throwable.toString(), null));
                    }));

    }
}
