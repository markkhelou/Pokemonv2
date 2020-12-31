package com.ouday.pokemon.list.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import com.ouday.pokemon.list.model.PokemonName

@Parcelize
data class PokemonListResponse (

    @SerializedName("count")
    var count: Int = 0,

    @SerializedName("next")
    var next: String? = null,

    @SerializedName("previous")
    var previous: String? = null,

    @SerializedName("results")
    var results: List<PokemonName>? = null

): Parcelable

