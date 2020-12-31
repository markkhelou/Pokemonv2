package com.ouday.pokemon.list

import com.ouday.pokemon.list.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonListUseCase {
    suspend fun fetchAllPokemons(offset: Int, limit: Int) : Flow<Result<List<Pokemon>?>>

}