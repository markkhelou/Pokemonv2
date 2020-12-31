package com.ouday.pokemon.list

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonListServiceImpl @Inject constructor(
    private val api: PokemonListApi
) : PokemonListService {

    override suspend fun fetchAllPokemons(offset: Int, limit: Int) = flow {
        emit(Result.success(api.fetchAllPokemons(offset, limit)))
    }.catch {
        emit(Result.failure(it))
    }

}