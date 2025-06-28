package com.covalsys.ttss_inv.data.network.models.post

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class PostAssetTagModel : ArrayList<PostAssetTagModel.PostAssetTagModelItem>(){
    @Parcelize
    data class PostAssetTagModelItem(
        @SerializedName("BatchNo")
        val batchNo: String,
        @SerializedName("CreatedDate")
        val createdDate: String,
        @SerializedName("DocEntry")
        val docEntry: String,
        @SerializedName("Length")
        val length: String,
        @SerializedName("Line")
        val line: String,
        @SerializedName("LoggedUser")
        val loggedUser: String,
        @SerializedName("LocationCode")
        val locationCode: String,
        @SerializedName("Quantity")
        val quantity: String,
        @SerializedName("ScanDate")
        val scanDate: String,
        @SerializedName("Status")
        val status: String,
        @SerializedName("Thick")
        val thick: String,
        @SerializedName("UpdatedDate")
        val updatedDate: String,
        @SerializedName("Width")
        val width: String
    ) : Parcelable
}