package com.covalsys.ttss_barcode.ui.asset_tag;

import com.covalsys.ttss_inv.data.network.models.get.GetAssetDocumentModel;

import java.util.List;

public interface AssetCountingNavigator {
    void onPostSuccess(String msg);
    void onGetSuccess(String msg);

    void onGetData(List<GetAssetDocumentModel.ResponseObject> responseObject, String bin, String DocStatus);

    void onPostFailed(String msg);
    void onGetFailed(String msg);
}
