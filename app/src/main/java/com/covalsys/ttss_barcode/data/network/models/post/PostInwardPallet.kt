package com.covalsys.ttss_barcode.data.network.models.post


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class PostInwardPallet : ArrayList<PostInwardPallet.PostInwardPalletItem>(){
    @Parcelize
    data class PostInwardPalletItem(
        @SerializedName("PalletCode")
        val palletCode: String,
        @SerializedName("PalletLocationCode")
        val palletLocationCode: String,
        @SerializedName("PastedBy")
        val pastedBy: String,
        @SerializedName("PostingDate")
        val postingDate: String
    ) : Parcelable
}