package com.covalsys.ttss_barcode.data.network.models.get

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class GetDatabaseModel(
    @SerializedName("DocEntry")
    val docEntry: String,
    @SerializedName("DocNum")
    val docNum: String,
    @SerializedName("responseObject")
    val responseObject: List<ResponseObject>,
    @SerializedName("statusCode")
    val statusCode: Int, // 0
    @SerializedName("statusMessage")
    val statusMessage: String // Success
) : Parcelable {
    @Parcelize
    data class ResponseObject(
        @SerializedName("DBID")
        val dBID: String, // ELEC
        @SerializedName("DBName")
        val dBName: String // VNC_ELECTRODES_LIVE_07072021
    ) : Parcelable{
        override fun toString(): String {
            return dBName
        }
    }
}