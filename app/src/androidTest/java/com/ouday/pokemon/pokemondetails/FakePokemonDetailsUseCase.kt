package com.ouday.pokemon.pokemondetails

import com.google.gson.Gson
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import com.ouday.pokemon.details.usecase.PokemonDetailsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object FakePokemonDetailsUseCase : PokemonDetailsUseCase {

    var useCaseState: UseCaseState = UseCaseState.SUCCESS

    private val pokemonDetails: PokemonDetailsResponse =
        Gson().fromJson(POKEMON_RESPONSE, PokemonDetailsResponse::class.java)

    override suspend fun fetchPokemonDetails(pokemonID: Int) = flow {
        delay(500)
        when (useCaseState) {
            UseCaseState.SUCCESS -> emit(Result.success(pokemonDetails))
            else -> emit(Result.failure(Exception("Backend error")))
        }
    }

    enum class UseCaseState { SUCCESS, ERROR }
}
