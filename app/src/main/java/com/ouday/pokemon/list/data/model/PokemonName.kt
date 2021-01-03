package com.ouday.pokemon.list.data.model

import com.google.gson.annotations.SerializedName

data class PokemonName (

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("url")
    var url: String? = null

)

