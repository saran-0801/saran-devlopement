package com.covalsys.ttss_barcode.data.preferences;

/**
 * Created by CBS on 11,November,2019
 */
public interface PreferenceHelper {

    boolean getIsLoggedIn();

    void setIsLoggedIn(boolean isLoggedIn);

    String getFcmToken();

    void setFcmToken(String token);

    String getUserEmail();

    void setUserEmail(String userId);

    String getUserType();

    void setUserType(String userId);

    String getAccessToken();

    void setAccessToken(String userId);

    String getUserDep();

    void setUserDep(String userId);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getSalesEmpCode();

    void setSalesEmpCode(String empCode);

    void setSalesEmpName(String empName);

    String getSalesEmpName();

    String getEmpTypeCode();

    void setEmpTypeCode(String typeCode);

    String getEmpTypeName();

    void setEmpTypeName(String typeName);

    String getMobileNo();

    void setMobileNo(String mobileNo);

    String getDbName();

    void setDbName(String dbName);

    String getDbId();

    void setDbId(String dbId);

    String getPassword();

    void setPassword(String password);

    String getSalesApprove();

    void setSalesApprove(String salesApprover);

    String getBpApprove();

    void setBpApprove(String bpApprover);

    String getPaymentApprove();

    void setPaymentApprove(String paymentApprover);

    String getUserCode();

    void setUserCode(String userCode);

    void deleteUserName();

    void deleteUserPassword();

    void deletePreferences();
}
