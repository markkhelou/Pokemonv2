package com.ouday.pokemon.list.data.model.response

import com.google.gson.annotations.SerializedName
import com.ouday.pokemon.list.data.model.PokemonName

data class PokemonListResponse (

    @SerializedName("count")
    var count: Int = 0,

    @SerializedName("next")
    var next: String? = null,

    @SerializedName("previous")
    var previous: String? = null,

    @SerializedName("results")
    var results: List<PokemonName>? = null

)
