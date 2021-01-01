package com.ouday.pokemon.details

import kotlinx.coroutines.flow.Flow

interface PokemonDetailsUseCase {
    suspend fun fetchPokemonDetails(pokemonID: Int) : Flow<Result<PokemonDetailsResponse>>
}