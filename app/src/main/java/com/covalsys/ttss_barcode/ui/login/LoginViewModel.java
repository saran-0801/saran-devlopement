package com.covalsys.ttss_barcode.ui.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.covalsys.ttss_barcode.data.database.AppDatabase;
import com.covalsys.ttss_barcode.data.database.RoomHelper;
import com.covalsys.ttss_barcode.data.network.ApiService;
import com.covalsys.ttss_barcode.data.network.Resource;
import com.covalsys.ttss_barcode.data.network.models.get.GetDatabaseModel;
import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;
import com.covalsys.ttss_barcode.data.network.models.post.LoginPostModel;
import com.covalsys.ttss_barcode.data.network.repository.Repository;
import com.covalsys.ttss_barcode.data.preferences.PreferenceHelper;
import com.covalsys.ttss_barcode.ui.base.BaseViewModel;
import com.covalsys.ttss_barcode.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public static final String TAG = "LoginViewModel";
    public MutableLiveData<String> error;
    public ObservableField<String> userName;
    public ObservableField<String> password;
    public ObservableField<String> type;
    public MutableLiveData<String> dbName;
    public MutableLiveData<String> dbId;
    public MutableLiveData<Boolean> progressBar;
    public MutableLiveData<Resource<LoginModel>> userLogin;
    public MutableLiveData<Resource<GetDatabaseModel>> dbList;

    @Inject
    public LoginViewModel(Repository repository, SchedulerProvider schedulerProvider,
                          PreferenceHelper preferenceHelper, AppDatabase database, RoomHelper helper, ApiService service) {
        super(repository, schedulerProvider, preferenceHelper, database, helper, service);

        type = new ObservableField<>("");
        userName = new ObservableField<>("");
        password = new ObservableField<>("");
        dbName = new MutableLiveData<>();
        dbId = new MutableLiveData<>();
        progressBar = new MutableLiveData<>();
        error = new MutableLiveData<>();
        userLogin = new MutableLiveData<>();
        dbList = new MutableLiveData<>();
    }


    public boolean isTypeValid() {
        return TextUtils.isEmpty(type.get());
    }

    public boolean isEmailValid() {
        return TextUtils.isEmpty(userName.get());
    }

    public boolean isPasswordValid() {
        return TextUtils.isEmpty(password.get());
    }

    public MutableLiveData<Resource<GetDatabaseModel>> getDBList() {
        return dbList;
    }

    public MutableLiveData<Resource<LoginModel>> getLogin() {
        return userLogin;
    }

    public void saveDetails(LoginModel.Result loginModels, Context context) {
            getPreferenceHelper().setIsLoggedIn(true);
            getPreferenceHelper().setUserCode(loginModels.getUser().getUserCode());
            getPreferenceHelper().setUserType(loginModels.getUser().getUserType());
            getPreferenceHelper().setFirstName(loginModels.getUser().getUserName());
            getPreferenceHelper().setUserDep(loginModels.getUser().getDepartment());
            getPreferenceHelper().setSalesEmpCode(String.valueOf(loginModels.getUser().getDocEntry()));
            getPreferenceHelper().setPassword(loginModels.getUser().getPassword());
            getPreferenceHelper().setAccessToken(loginModels.getToken());
    }

    public void forgotPassword() {
        getNavigator().openForgotPassword();
    }

    public void login() {

         if (isEmailValid()) {
            error.setValue("Username should not be empty");
        } else if (isPasswordValid()) {
            error.setValue("Password should not be empty");
        } else {

             LoginPostModel model = new LoginPostModel(password.get(), userName.get());
            progressBar.setValue(true);
            getCompositeDisposable().add(getApiService()
                    .login(model)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(loginModel -> {
                        progressBar.setValue(false);
                        if (loginModel.isSuccess()) {
                            userLogin.postValue(Resource.success(loginModel));
                        } else {
                            userLogin.postValue(Resource.error(loginModel.getMessage().getDescription(), null));
                        }

                    }, throwable -> {
                        progressBar.setValue(false);
                        Log.e(TAG, throwable.toString());
                        userLogin.postValue(Resource.error(throwable.toString(), null));
                    }));
        }
    }
}
