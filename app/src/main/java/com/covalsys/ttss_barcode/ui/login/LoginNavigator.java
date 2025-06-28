package com.covalsys.ttss_barcode.ui.login;


import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;

import java.util.List;

/**
 * Created by CBS on 11-07-2020.
 */
public interface LoginNavigator {

    void openForgotPassword();

    void showLoading();

    void hideLoading();

    void onPostSuccess(List<LoginModel> loginModel);

    void onPostFailed(String msg);

}
