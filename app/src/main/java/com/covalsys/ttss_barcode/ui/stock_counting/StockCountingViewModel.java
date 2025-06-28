package com.covalsys.ttss_barcode.ui.stock_counting;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.database.entities.StockCountLine;
import com.covalsys.ttss_barcode.data.database.entities.StockHeader;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.Resource;
import com.covalsys.ttss_barcode.data.network.models.get.GetDocumentModel;
import com.covalsys.ttss_barcode.data.network.models.get.GetLocationModel;
import com.covalsys.ttss_barcode.data.network.models.post.PostInventoryCount;
import com.covalsys.ttss_barcode.data.network.models.post.PostStockCountModel;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.utils.DateUtils;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class StockCountingViewModel extends BaseViewModel<StockCountingNavigator> {

    public static final String TAG = StockCountingViewModel.class.getSimpleName();
    public MutableLiveData<Boolean> progressBar;
    public MediatorLiveData<List<StockCountLine>> model = new MediatorLiveData<>();
    public MediatorLiveData<List<StockHeader>> hModel = new MediatorLiveData<>();
    public MutableLiveData<Context> mContext;
    public MutableLiveData<Resource<GetLocationModel>> location;
    public MutableLiveData<Resource<PostStockCountModel>> dDocLine;
    public MutableLiveData<String> nextDate;

    @Inject
    public StockCountingViewModel(Repository repository, SchedulerProvider schedulerProvider,
                                  PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
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

    public void refreshData(){
        deliveryList();
    }

    public void refreshHeaderData(){
        LiveData<List<StockHeader>> listLiveData = getDatabase().stockHeaderDao().getData();
        hModel.addSource(listLiveData, stockHeaders -> {
            hModel.setValue(stockHeaders);
        });
    }

    public void setContext(Context context) {
        mContext.setValue(context);
    }

    private void deliveryList() {
        LiveData<List<StockCountLine>> listLiveData = getDatabase().stockCountLineDao().getData();
        model.addSource(listLiveData, pallets -> {
            model.setValue(pallets);
        });
    }

    public MediatorLiveData<List<StockCountLine>> getScanData() {
        return model;
    }

    public MediatorLiveData<List<StockHeader>> getHeaderData() {
        return hModel;
    }

    public String getDocStatus() {
        return getDatabase().stockHeaderDao().getStatus();
    }

    public int getScannedCount(){
        return getDatabase().stockCountLineDao().getScannedDataSize();
    }

    public LiveData<Resource<GetLocationModel>> getLocation() {
        return location;
    }

    public void getDocument(String location){
        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getItemDetails(location)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getDocumentModel -> {
                    progressBar.setValue(false);
                    if (getDocumentModel.getStatusCode() == 0) {
                        if(getDocumentModel.getDocStatus().equalsIgnoreCase("O")) {
                            insertOpenData(getDocumentModel.getResponseObject(), location);
                        }else{
                            insertClosedData(getDocumentModel.getResponseObject(), location);
                        }
                        getNavigator().onGetSuccess(getDocumentModel.getStatusMessage());
                    } else {
                        Log.e("TAG_B", getDocumentModel.getStatusMessage());
                        getDatabase().stockCountLineDao().deleteAllData();
                        getDatabase().stockHeaderDao().deleteAllData();
                        getNavigator().onGetFailed(getDocumentModel.getStatusMessage());
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    Log.e("TAG_B", throwable.toString());
                    getDatabase().stockCountLineDao().deleteAllData();
                    getDatabase().stockHeaderDao().deleteAllData();
                    getNavigator().onPostFailed("No data found.");
                }));
    }

    private void insertOpenData(List<GetDocumentModel.ResponseObject> value, String bin) {
        getDatabase().stockCountLineDao().deleteAllData();
        getDatabase().stockHeaderDao().deleteAllData();
        int docNum = 0;
        for(GetDocumentModel.ResponseObject ob: value){
            getDatabase().stockCountLineDao().add(new StockCountLine(ob.getLine(),ob.getDocNum(), "", (float) ob.getSysQty(), (float) ob.getActQty(), ob.getItemCode(), ob.getItemName(), "", ob.getScanDate(), "P"));
            docNum = ob.getDocNum();
        }
        getDatabase().stockHeaderDao().add(new StockHeader(docNum, bin, "", "", "O"));
        refreshData();
        refreshHeaderData();
    }

    private void insertClosedData(List<GetDocumentModel.ResponseObject> value, String bin) {
        getDatabase().stockCountLineDao().deleteAllData();
        getDatabase().stockHeaderDao().deleteAllData();
        int docNum = 0;
        for(GetDocumentModel.ResponseObject ob: value){
            getDatabase().stockCountLineDao().add(new StockCountLine(ob.getLine(),ob.getDocNum(), "", (float) ob.getSysQty(), (float) ob.getActQty(), ob.getItemCode(), ob.getItemName(), "", ob.getScanDate(), "A"));
            docNum = ob.getDocNum();
        }
        getDatabase().stockHeaderDao().add(new StockHeader(docNum, bin, "", "", "C"));
        refreshData();
        refreshHeaderData();
    }

    private void getCustomersDetails() {

        progressBar.setValue(true);
        getCompositeDisposable().add(getApiService()
                .getLocationModel()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getLocationModel -> {
                    progressBar.setValue(false);
                    if (getLocationModel.getStatusCode() == 0) {
                        location.postValue(Resource.success(getLocationModel));
                    } else {
                        location.postValue(Resource.error(getLocationModel.getStatusMessage(), null));
                    }
                }, throwable -> {
                    progressBar.setValue(false);
                    location.postValue(Resource.error(throwable.toString(), null));
                }));
    }

    public LiveData<String> nextDate() {
        return nextDate;
    }

    public void documentDate() {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            nextDate.postValue(DateUtils.dateFormat().format(myCalendar.getTime()));
        };

        DatePickerDialog datePicker = new DatePickerDialog(mContext.getValue(), R.style.DialogTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        //datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
        //datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    public void postDataToServer(String stRemarks, String stLocation, String stDocDate){

        progressBar.setValue(true);

        List<PostInventoryCount.Line> CList = new ArrayList<>();
        List<StockCountLine> listData = getDatabase().stockCountLineDao().getDataList();
        String DocNum = "0";
            for (StockCountLine item : listData) {
                DocNum = String.valueOf(item.getDocNum());
                CList.add(new PostInventoryCount.Line(item.getActQty(), String.valueOf(item.getDocNum()), item.getItemCode(), item.getItemName(), String.valueOf(item.getSlno()), DateUtils.convertDateFormat13(item.getScanDate()), item.getSysQty()));
            }

        PostInventoryCount count = new PostInventoryCount(DateUtils.currentDateYYYY(), DateUtils.currentDateYYYY(), CList, stLocation, getPreferenceHelper().getSalesEmpCode(), DocNum, DateUtils.currentDateYYYY());

            getCompositeDisposable().add(getApiService().addStockCount(count)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(basePostResponse -> {
                        progressBar.setValue(false);
                        if (basePostResponse.getStatusCode() == 0) {
                            getDatabase().stockCountLineDao().deleteAllData();
                            getDatabase().stockHeaderDao().deleteAllData();
                            getDatabase().stockCountScanLineDao().deleteAllData();
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
