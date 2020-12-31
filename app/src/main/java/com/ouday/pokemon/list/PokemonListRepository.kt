package com.ouday.pokemon.list

import com.ouday.pokemon.list.model.NamedResponseModel
import kotlinx.coroutines.flow.Flow

interface PokemonListRepository {
    suspend fun fetchAllPokemons(offset: Int, limit: Int) : Flow<Result<List<NamedResponseModel>?>>
}