package com.ouday.pokemon.list

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ouday.pokemon.list.model.PokemonName
import com.ouday.pokemon.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

private const val OFFSET = 0
private const val LIMIT = 20
private const val POKEMON_NAME = "Pokemon Test"
private const val POKEMON_ID = 13
private const val POKEMON_URL = "anyUrl.com/any/$POKEMON_ID/"

class PokemonListUseCaseShould : BaseUnitTest() {

    private val repo: PokemonListRepository = mock()
    private val pokemonName = mock<PokemonName>()

    private val backendErrorException = RuntimeException("Backend error")

    init {
        whenever(pokemonName.name).thenReturn(POKEMON_NAME)
        whenever(pokemonName.url).thenReturn(POKEMON_URL)
    }

    @Test
    fun fetchAllPokemonsFromUseCase() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        useCase.fetchAllPokemons(OFFSET, LIMIT)
        verify(repo, times(1)).fetchAllPokemons(OFFSET, LIMIT)
    }

    @Test
    fun emitPokemonsListFromUseCase() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        assertEquals(
            POKEMON_NAME,
            useCase.fetchAllPokemons(OFFSET, LIMIT).first().getOrNull()?.get(0)?.name
        )
        assertEquals(
            POKEMON_ID,
            useCase.fetchAllPokemons(OFFSET, LIMIT).first().getOrNull()?.get(0)?.id
        )
        assertEquals(
            "$URL_IMAGE$POKEMON_ID.png",
            useCase.fetchAllPokemons(OFFSET, LIMIT).first().getOrNull()?.get(0)?.pokemonImage
        )
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val useCase = mockErrorCase()
        assertEquals(
            backendErrorException,
            useCase.fetchAllPokemons(OFFSET, LIMIT).first().exceptionOrNull()
        )
    }

    private suspend fun mockErrorCase(): PokemonListUseCaseImpl {
        whenever(repo.fetchAllPokemons(OFFSET, LIMIT)).thenReturn(
            flow {
                emit(Result.failure(backendErrorException))
            }
        )
        return PokemonListUseCaseImpl(repo)
    }

    private suspend fun mockSuccessfulCase(): PokemonListUseCaseImpl {
        whenever(repo.fetchAllPokemons(OFFSET, LIMIT))
            .thenReturn(flow {
                emit(Result.success(arrayListOf(pokemonName)))
            })
        return PokemonListUseCaseImpl(repo)
    }

}