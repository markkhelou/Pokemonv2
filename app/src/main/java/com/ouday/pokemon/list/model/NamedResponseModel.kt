package com.ouday.pokemon.list.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NamedResponseModel (

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("url")
    var url: String? = null

): Parcelable

