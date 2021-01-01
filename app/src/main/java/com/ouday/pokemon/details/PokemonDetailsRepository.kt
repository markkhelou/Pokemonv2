package com.ouday.pokemon.details

import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {
    suspend fun fetchPokemonDetails(pokemonID: Int) : Flow<Result<PokemonDetailsResponse>>
}