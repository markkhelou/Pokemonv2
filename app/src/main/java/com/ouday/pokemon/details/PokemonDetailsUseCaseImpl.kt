package com.ouday.pokemon.details

import javax.inject.Inject

class PokemonDetailsUseCaseImpl @Inject constructor(private val repository: PokemonDetailsRepository) :
    PokemonDetailsUseCase {
    override suspend fun fetchPokemonDetails(pokemonID: Int) = repository.fetchPokemonDetails(pokemonID)
}