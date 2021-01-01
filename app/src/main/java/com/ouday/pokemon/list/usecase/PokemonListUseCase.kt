package com.ouday.pokemon.list.usecase

import com.ouday.pokemon.list.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonListUseCase {
    suspend fun fetchAllPokemons(offset: Int, limit: Int) : Flow<Result<List<Pokemon>?>>
}