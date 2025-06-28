package com.covalsys.ttss_barcode.data.network;

import com.covalsys.ttss_barcode.data.network.models.BarcodeList.BarcodeListModel;
import com.covalsys.ttss_barcode.data.network.models.BarcodeList.ResultItem;
import com.covalsys.ttss_barcode.data.network.models.HeaderModel.HeaderBarcodeModel;
import com.covalsys.ttss_barcode.data.network.models.HeaderModel.Result;
import com.covalsys.ttss_barcode.data.network.models.PostHeader.PostHeaderModel;
import com.covalsys.ttss_barcode.data.network.models.PostList.PostListModel;
import com.covalsys.ttss_barcode.data.network.models.get.GetDocumentModel;
import com.covalsys.ttss_barcode.data.network.models.get.GetLocationModel;
import com.covalsys.ttss_barcode.data.network.models.get.LoginModel;
import com.covalsys.ttss_barcode.data.network.models.get.gate.AreaOfWorkModel;
import com.covalsys.ttss_barcode.data.network.models.post.BasePostResponse;
import com.covalsys.ttss_barcode.data.network.models.post.LoginPostModel;
import com.covalsys.ttss_barcode.data.network.models.post.PostInventoryCount;
import com.covalsys.ttss_barcode.data.network.models.post.PostInwardPallet;
import com.covalsys.ttss_inv.data.network.models.get.GetAssetDocumentModel;
import com.covalsys.ttss_inv.data.network.models.get.GetAssetLocationModel;
import com.covalsys.ttss_inv.data.network.models.post.PostAssetTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @POST("/api/auth/applogin")
    Single<LoginModel> login(@Body LoginPostModel model);

    @GET("api/Inventory/getLocation")
    Observable<GetLocationModel> getLocationModel();

    @GET("/NoMode/api/Inventory/getBinItemDetails")
    Observable<GetDocumentModel> getItemDetails(@Query("binCode") String binCode);

    @POST("/NoMode/api/Inventory/addStockCount")
    Observable<BasePostResponse> addStockCount(@Body PostInventoryCount stock);

    @GET("api/Asset/getAssetLocation")
    Observable<GetAssetLocationModel> getAssetLocationModel();

    @POST("/NoMode/api/Asset/addAssetTag")
    Observable<BasePostResponse> addAssetTag(@Body PostAssetTag asset);

    @POST("/NoMode/api/Asset/updateAssetTag")
    Observable<BasePostResponse> updateAssetTag(@Body PostAssetTag asset);

    @GET("/NoMode/api/Asset/getBinItemDetails")
    Observable<GetAssetDocumentModel> getAssetItemDetails(@Query("binCode") String binCode,@Query("Status") String status);

    @POST("/NoMode/api/Login/addToInward")
    Observable<BasePostResponse> addInward(@Query("UserType") String userType,  @Body ArrayList<PostInwardPallet.PostInwardPalletItem> pallet);

    @POST("/NoMode/api/Login/addToOutward")
    Observable<BasePostResponse> addOutward(@Query("UserType") String userType, @Body ArrayList<PostInwardPallet.PostInwardPalletItem> pallet);

    @GET("/api/admin/areaofwork")
    Observable<AreaOfWorkModel> getAreaOfWork(@HeaderMap Map<String, String> headers);

    @GET("/api/admin/barcodeheader")
    Observable<HeaderBarcodeModel> getHeaderBarCode(@HeaderMap Map<String, String> headers,
                                                    @Query("docnum") String docNum);

    @GET("/api/admin/barcodelist")
    Observable<BarcodeListModel> getBarCodeList(@HeaderMap Map<String, String> headers,
                                                @Query("docentry") String docEntry,
                                                @Query("groupnum") Integer groupnum);

    @POST("/api/admin/barcodeheader")
    Observable<PostHeaderModel> sendHeaderData(@HeaderMap Map<String, String> headers, @Body Result result);

    @POST("/api/admin/barcodelist")
    Observable<PostListModel> sendBarCodeList(@HeaderMap Map<String, String> headers,
                                        @Body List<ResultItem> resultItemList,
                                        @Query("groupnum") Integer groupNum);
}
