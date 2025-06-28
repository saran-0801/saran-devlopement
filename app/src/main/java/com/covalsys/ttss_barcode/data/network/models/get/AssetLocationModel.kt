package com.covalsys.ttss_inv.data.network.models.get

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AssetLocationModel(
    @SerializedName("LocationCode")
    val locationCode: String
) : Parcelable