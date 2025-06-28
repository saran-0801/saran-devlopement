package com.covalsys.ttss_barcode.ui.stock_counting;

/**
 * Created by Prasanth on 09-07-2020.
 */
public interface StockCountingNavigator {

    void onPostSuccess(String msg);
    void onGetSuccess(String msg);

    void onPostFailed(String msg);
    void onGetFailed(String msg);
}
