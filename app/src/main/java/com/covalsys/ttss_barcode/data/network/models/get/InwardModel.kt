package com.covalsys.ttss_barcode.data.network.models.get


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class InwardModel(
    @SerializedName("PalletCode")
    val palletCode: String,
    @SerializedName("PalletLocationCode")
    val palletLocationCode: String,
    @SerializedName("PastedBy")
    val pastedBy: String,
    @SerializedName("PostingDate")
    val postingDate: String,
    @SerializedName("Slno")
    val slno: String
) : Parcelable