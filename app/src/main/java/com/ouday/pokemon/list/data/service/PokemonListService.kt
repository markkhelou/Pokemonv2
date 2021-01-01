package com.ouday.pokemon.list.data.service

import com.ouday.pokemon.list.data.model.response.PokemonListResponse
import kotlinx.coroutines.flow.Flow

interface PokemonListService {
    suspend fun fetchAllPokemons(offset: Int, limit: Int) : Flow<Result<PokemonListResponse>>
}