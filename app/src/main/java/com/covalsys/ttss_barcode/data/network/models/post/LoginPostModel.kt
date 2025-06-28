package com.covalsys.ttss_barcode.data.network.models.post


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginPostModel(
    @SerializedName("password")
    val password: String,
    @SerializedName("userName")
    val userName: String
) : Parcelable