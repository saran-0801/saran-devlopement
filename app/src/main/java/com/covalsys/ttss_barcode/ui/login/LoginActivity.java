package com.covalsys.ttss_barcode.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import com.covalsys.ttss_barcode.BR;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;
import com.covalsys.ttss_barcode.databinding.ActivityLoginBinding;
import com.covalsys.ttss_barcode.ui.ViewModelProviderFactory;
import com.covalsys.ttss_barcode.ui.base.BaseActivity;
import com.covalsys.ttss_barcode.ui.main.MainActivity;
import java.util.List;
import javax.inject.Inject;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements LoginNavigator {

    @Inject
    ViewModelProviderFactory factory;

    private BroadcastReceiver mBroadcastReceiver;
    private String token;
    private LoginViewModel mLoginViewModel;
    private String dbCode,dbName;

    @Override
    public int getBindingVariable() {
        return BR.loginViewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        return mLoginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel.setNavigator(this);
        initObservables();
    }

    public void initObservables() {
        mLoginViewModel.error.removeObservers(this);
        mLoginViewModel.error.observe(this, this::showSnackBar);

        mLoginViewModel.progressBar.observe(this, aBoolean -> {
            if (aBoolean) {
               showLoading();
            } else {
               hideLoading();
            }
        });

        mLoginViewModel.getLogin().removeObservers(this);
        mLoginViewModel.getLogin().observe(this, loginModelResource -> {
            switch (loginModelResource.status) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    hideLoading();
                    assert loginModelResource.data != null;
                    setLoginResponse(loginModelResource.data.getResult());
                    break;
                case ERROR:
                    hideLoading();
                    showSnackBar(loginModelResource.getMessage());
                    break;
                default:
                    break;
            }
        });
    }

    public void setLoginResponse(LoginModel.Result loginResponse) {

        mLoginViewModel.saveDetails(loginResponse, LoginActivity.this);

        Intent intent = MainActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();

    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void onPostSuccess(List<LoginModel> loginModel) {

    }

    @Override
    public void onPostFailed(String msg) {

    }

    @Override
    public void openForgotPassword() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
