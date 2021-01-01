package com.ouday.pokemon.list.data.repo

import com.ouday.pokemon.list.data.service.PokemonListService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonListRepositoryImpl @Inject constructor(
    private val service: PokemonListService
) : PokemonListRepository {

    override suspend fun fetchAllPokemons(
        offset: Int,
        limit: Int
    ) = service.fetchAllPokemons(offset, limit).map {
        if (it.isSuccess)
            Result.success(it.getOrNull()?.results)
        else
            Result.failure(it.exceptionOrNull()!!)
    }
}