package com.ouday.pokemon.details.usecase

import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsUseCase {
    suspend fun fetchPokemonDetails(pokemonID: Int) : Flow<Result<PokemonDetailsResponse>>
}