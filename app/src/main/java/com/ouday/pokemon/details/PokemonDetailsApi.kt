package com.ouday.pokemon.details

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PokemonDetailsApi {
    @GET("pokemon/{pokemonID}")
    @Headers("Cache-control: no-cache")
    suspend fun fetchPokemonDetailsAsync(@Path("pokemonID") pokemonID: Int): PokemonDetailsResponse
}