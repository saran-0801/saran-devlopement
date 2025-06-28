package com.covalsys.ttss_barcode.data.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.Resource;
import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;
import com.covalsys.ttss_barcode.data.network.models.post.BasePostResponse;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class Repository {

    private static final String TAG = Repository.class.getSimpleName();
    private ApiService apiService;
    private final MutableLiveData<Resource<BasePostResponse>> mutablePostResponse;
    private final MutableLiveData<Resource<LoginModel>> mutableLoginResponse = new MutableLiveData<>();;
    private final MutableLiveData<Boolean> progressbarObservable;

    @Inject
    public Repository(ApiService apiService) {
        this.apiService = apiService;
        mutablePostResponse = new MutableLiveData<>();
        progressbarObservable = new MutableLiveData<>(false);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return progressbarObservable;
    }

}
