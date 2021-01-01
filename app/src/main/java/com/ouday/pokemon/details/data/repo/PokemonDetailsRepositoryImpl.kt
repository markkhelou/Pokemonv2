package com.ouday.pokemon.details.data.repo

import com.ouday.pokemon.details.data.service.PokemonDetailsService
import javax.inject.Inject

class PokemonDetailsRepositoryImpl @Inject constructor(private val service: PokemonDetailsService) :
    PokemonDetailsRepository {

    override suspend fun fetchPokemonDetails(pokemonID: Int) =
        service.fetchPokemonDetails(pokemonID)
}