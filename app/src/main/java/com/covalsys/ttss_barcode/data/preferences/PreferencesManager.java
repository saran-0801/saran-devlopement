package com.covalsys.ttss_barcode.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.covalsys.ttss_barcode.di.AppContext;
import com.covalsys.ttss_barcode.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PreferencesManager implements PreferenceHelper {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_LOGGED_IN_MODE_TTSS";
    private static final String PREF_KEY_USER_CODE = "PREF_KEY_USER_CODE";
    private static final String PREF_KEY_USER_MOBILE = "PREF_KEY_CURRENT_MOBILE";
    private static final String PREF_KEY_USER_FIRST_NAME = "PREF_KEY_CURRENT_USER_FIRST_NAME";
    private static final String PREF_KEY_USER_LAST_NAME = "PREF_KEY_CURRENT_USER_LAST_NAME";
    private static final String PREF_KEY_EMP_NAME = "PREF_KEY_EMP_NAME";
    private static final String PREF_KEY_USER_PWD = "PREF_KEY_CURRENT_USER_PWD";
    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_USER_TYPE = "PREF_KEY_CURRENT_USER_TYPE";
    private static final String PREF_KEY_USER_DEP = "PREF_KEY_CURRENT_USER_DEP";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_FIRST_TIME = "PREF_KEY_FIRST_TIME";
    private static final String PREF_KEY_USER_PROFILE_PIC_URL = "PREF_KEY_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_COACH_MARK = "PREF_KEY_COACH_MARK";
    private static final String PREF_KEY_DB_NAME = "PREF_KEY_DB_NAME";
    private static final String PREF_KEY_DB_ID = "PREF_KEY_DB_ID";
    private static final String PREF_KEY_SALES_EMP_CODE = "PREF_KEY_SALES_EMP_CODE";
    private static final String PREF_KEY_EMP_TYPE_CODE = "PREF_KEY_EMP_TYPE_CODE";
    private static final String PREF_KEY_EMP_TYPE_NAME = "PREF_KEY_EMP_TYPE_NAME";
    private static final String PREF_KEY_FCM_TOKEN = "PREF_KEY_FCM_TOKEN";
    private static final String PREF_KEY_SALES_APPROVER = "PREF_KEY_SALES_APPROVER";
    private static final String PREF_KEY_PAYMENT_APPROVER = "PREF_KEY_PAYMENT_APPROVER";
    private static final String PREF_KEY_BP_APPROVER = "PREF_KEY_BP_APPROVER";

    private final SharedPreferences mPrefs;

    @Inject
    public PreferencesManager(@AppContext Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        //mAppContext = context;
    }

    @Override
    public boolean getIsLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_USER_LOGGED_IN_MODE,false);
    }

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        mPrefs.edit().putBoolean(PREF_KEY_USER_LOGGED_IN_MODE,isLoggedIn).apply();
    }

    @Override
    public String getFcmToken() {
        return mPrefs.getString(PREF_KEY_FCM_TOKEN,null);
    }

    @Override
    public void setFcmToken(String token) {
        mPrefs.edit().putString(PREF_KEY_FCM_TOKEN,token).apply();
    }

    @Override
    public String getUserEmail() {
        return mPrefs.getString(PREF_KEY_USER_EMAIL,null);
    }

    @Override
    public void setUserEmail(String userId) {
        mPrefs.edit().putString(PREF_KEY_USER_EMAIL,userId).apply();
    }

    @Override
    public String getUserType() {
        return mPrefs.getString(PREF_KEY_USER_TYPE,null);
    }

    @Override
    public void setUserType(String userId) {
        mPrefs.edit().putString(PREF_KEY_USER_TYPE,userId).apply();
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN,null);
    }

    @Override
    public void setAccessToken(String userId) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN,userId).apply();
    }

    @Override
    public String getUserDep() {
        return mPrefs.getString(PREF_KEY_USER_DEP,null);
    }

    @Override
    public void setUserDep(String userId) {
        mPrefs.edit().putString(PREF_KEY_USER_DEP,userId).apply();
    }

    @Override
    public String getFirstName() {
        return mPrefs.getString(PREF_KEY_USER_FIRST_NAME,null);
    }

    @Override
    public void setFirstName(String firstName) {
        mPrefs.edit().putString(PREF_KEY_USER_FIRST_NAME,firstName).apply();
    }

    @Override
    public String getLastName() {
        return mPrefs.getString(PREF_KEY_USER_LAST_NAME,null);
    }

    @Override
    public void setLastName(String lastName) {
        mPrefs.edit().putString(PREF_KEY_USER_LAST_NAME,lastName).apply();
    }


    @Override
    public String getSalesEmpCode() {
        return mPrefs.getString(PREF_KEY_SALES_EMP_CODE,null);
    }

    @Override
    public void setSalesEmpCode(String userId) {
        mPrefs.edit().putString(PREF_KEY_SALES_EMP_CODE,userId).apply();
    }

    @Override
    public void setSalesEmpName(String empName) {
        mPrefs.edit().putString(PREF_KEY_EMP_NAME,empName).apply();
    }

    @Override
    public String getSalesEmpName() {
        return mPrefs.getString(PREF_KEY_EMP_NAME,null);
    }

    @Override
    public String getEmpTypeCode() {
        return mPrefs.getString(PREF_KEY_EMP_TYPE_CODE,null);
    }

    @Override
    public void setEmpTypeCode(String typeCode) {
        mPrefs.edit().putString(PREF_KEY_EMP_TYPE_CODE,typeCode).apply();
    }

    @Override
    public String getEmpTypeName() {
        return mPrefs.getString(PREF_KEY_EMP_TYPE_NAME,null);
    }

    @Override
    public void setEmpTypeName(String typeName) {
        mPrefs.edit().putString(PREF_KEY_EMP_TYPE_NAME,typeName).apply();
    }


    @Override
    public String getMobileNo() {
        return mPrefs.getString(PREF_KEY_USER_MOBILE,null);
    }

    @Override
    public void setMobileNo(String mobileNo) {
        mPrefs.edit().putString(PREF_KEY_USER_MOBILE,mobileNo).apply();
    }

    @Override
    public String getDbName() {
        return mPrefs.getString(PREF_KEY_DB_NAME,null);
    }

    @Override
    public void setDbName(String dbName) {
        mPrefs.edit().putString(PREF_KEY_DB_NAME,dbName).apply();
    }

    @Override
    public String getDbId() {
        return mPrefs.getString(PREF_KEY_DB_ID,null);
    }

    @Override
    public void setDbId(String dbId) {
        mPrefs.edit().putString(PREF_KEY_DB_ID,dbId).apply();
    }

    @Override
    public String getPassword() {
        return mPrefs.getString(PREF_KEY_USER_PWD,null);
    }

    @Override
    public void setPassword(String password) {
        mPrefs.edit().putString(PREF_KEY_USER_PWD,password).apply();
    }

    @Override
    public String getSalesApprove() {
        return mPrefs.getString(PREF_KEY_SALES_APPROVER,null);
    }

    @Override
    public void setSalesApprove(String salesApprover) {
        mPrefs.edit().putString(PREF_KEY_SALES_APPROVER,salesApprover).apply();
    }

    @Override
    public String getBpApprove() {
        return mPrefs.getString(PREF_KEY_BP_APPROVER,null);
    }

    @Override
    public void setBpApprove(String bpApprover) {
        mPrefs.edit().putString(PREF_KEY_BP_APPROVER,bpApprover).apply();
    }

    @Override
    public String getPaymentApprove() {
        return mPrefs.getString(PREF_KEY_PAYMENT_APPROVER,null);
    }

    @Override
    public void setPaymentApprove(String paymentApprover) {
        mPrefs.edit().putString(PREF_KEY_PAYMENT_APPROVER,paymentApprover).apply();
    }

    @Override
    public String getUserCode() {
        return mPrefs.getString(PREF_KEY_USER_CODE,null);
    }

    @Override
    public void setUserCode(String userCode) {
        mPrefs.edit().putString(PREF_KEY_USER_CODE,userCode).apply();
    }

    @Override
    public void deleteUserName() {
        mPrefs.edit().remove(PREF_KEY_USER_FIRST_NAME).apply();
    }

    @Override
    public void deleteUserPassword() {
        mPrefs.edit().remove(PREF_KEY_USER_PWD).apply();
    }

    @Override
    public void deletePreferences() {
        mPrefs.edit().clear().apply();
    }
}
