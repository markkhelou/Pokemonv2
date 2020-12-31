package com.ouday.pokemon.list

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import com.ouday.pokemon.list.model.response.PokemonListResponse

interface PokemonListApi {

    @GET("/pokemon")
    @Headers("Cache-control: no-cache")
    suspend fun fetchAllPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse

}