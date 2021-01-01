package com.ouday.pokemon.details.usecase

import com.ouday.pokemon.details.data.repo.PokemonDetailsRepository
import com.ouday.pokemon.details.usecase.PokemonDetailsUseCase
import javax.inject.Inject

class PokemonDetailsUseCaseImpl @Inject constructor(private val repository: PokemonDetailsRepository) :
    PokemonDetailsUseCase {
    override suspend fun fetchPokemonDetails(pokemonID: Int) = repository.fetchPokemonDetails(pokemonID)
}