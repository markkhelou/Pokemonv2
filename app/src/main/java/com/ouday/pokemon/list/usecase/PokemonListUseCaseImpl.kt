package com.ouday.pokemon.list.usecase

import com.ouday.pokemon.list.data.repo.PokemonListRepository
import com.ouday.pokemon.list.data.model.Pokemon
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val URL_IMAGE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/ruby-sapphire/shiny/"

class PokemonListUseCaseImpl @Inject constructor(
    private val repo: PokemonListRepository
): PokemonListUseCase {

    override suspend fun fetchAllPokemons(
        offset: Int,
        limit: Int
    ) = repo.fetchAllPokemons(offset, limit).map {
        if (it.isSuccess)
            Result.success(it.getOrNull()?.map {pokemonName ->
                    val pokemonId = getPokemonIdFromUrl(pokemonName.url)
                    Pokemon(
                        id = pokemonId,
                        name = pokemonName.name,
                        pokemonImage = getPokemonImageUrl(pokemonId)
                    )
            })
        else
            Result.failure(it.exceptionOrNull()!!)
    }

    private fun getPokemonIdFromUrl(pokemonUrl: String?): Int?{
        var url = pokemonUrl?.trim().toString()
        url = if (url[url.length-1] == '/') url.substring(0, url.length-1) else url
        val arr = url.split("/")
        return arr.get(arr.size-1).toIntOrNull()
    }

    private fun getPokemonImageUrl(pokemonId: Int?) = "$URL_IMAGE$pokemonId.png"

}