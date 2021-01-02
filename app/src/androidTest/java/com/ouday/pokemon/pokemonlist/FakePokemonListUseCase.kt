package com.ouday.pokemon.pokemonlist

import com.ouday.pokemon.list.data.model.Pokemon
import com.ouday.pokemon.list.usecase.PokemonListUseCase
import com.ouday.pokemon.list.usecase.URL_IMAGE
import kotlinx.coroutines.flow.flow

const val POKEMON_NAME = "bulbasaur"
const val POKEMON_IMAGE = "${URL_IMAGE}1.png"
const val POKEMON_ID = 1

object FakePokemonListUseCase : PokemonListUseCase {
    var useCaseState: UseCaseState =
        UseCaseState.SUCCESS
    override suspend fun fetchAllPokemons(offset: Int, limit: Int) = flow {
        when (useCaseState) {
            UseCaseState.SUCCESS -> emit(
                Result.success(
                    arrayListOf(
                        Pokemon(
                            POKEMON_ID,
                            POKEMON_NAME,
                            POKEMON_IMAGE
                        )
                    )
                )
            )
            else -> emit(Result.failure(Exception("Backend error")))
        }
    }

    enum class UseCaseState { SUCCESS, ERROR }
}
