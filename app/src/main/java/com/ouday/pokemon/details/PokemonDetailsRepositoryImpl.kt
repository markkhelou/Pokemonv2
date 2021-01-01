package com.ouday.pokemon.details

import javax.inject.Inject

class PokemonDetailsRepositoryImpl @Inject constructor(private val service: PokemonDetailsService) :
    PokemonDetailsRepository {

    override suspend fun fetchPokemonDetails(pokemonID: Int) =
        service.fetchPokemonDetails(pokemonID)
}