package com.ouday.pokemon.details.data.repo

import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {
    suspend fun fetchPokemonDetails(pokemonID: Int) : Flow<Result<PokemonDetailsResponse>>
}