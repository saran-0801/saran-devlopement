package com.covalsys.ttss_barcode.ui.gate;

import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;

import java.util.List;

public interface GateNavigator {

    void showLoading();

    void hideLoading();

    void onPostSuccess(List<LoginModel> loginModel);

    void onPostFailed(String msg);

}
