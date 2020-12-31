package com.ouday.pokemon.list

import com.ouday.pokemon.list.model.response.PokemonListResponse
import kotlinx.coroutines.flow.Flow

interface PokemonListService {
    suspend fun fetchAllPokemons(offset: Int, limit: Int) : Flow<Result<PokemonListResponse>>
}