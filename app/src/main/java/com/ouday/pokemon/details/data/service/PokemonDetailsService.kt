package com.ouday.pokemon.details.data.service

import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsService {
    suspend fun fetchPokemonDetails(pokemonID: Int) : Flow<Result<PokemonDetailsResponse>>
}