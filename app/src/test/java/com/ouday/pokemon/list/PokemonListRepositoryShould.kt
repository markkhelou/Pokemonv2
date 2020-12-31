package com.ouday.pokemon.list

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ouday.pokemon.list.model.PokemonName
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import com.ouday.pokemon.list.model.response.PokemonListResponse
import com.ouday.pokemon.utils.BaseUnitTest
import kotlinx.coroutines.flow.flow

private const val OFFSET = 0
private const val LIMIT = 20

class PokemonListRepositoryShould : BaseUnitTest() {

    private val service: PokemonListService = mock()
    private val pokemonResponse: PokemonListResponse = mock()
    private val pokemonNames = mock<List<PokemonName>>()
    private val backendErrorException = RuntimeException("Backend error")

    @Test
    fun fetchAllPokemonssFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.fetchAllPokemons(OFFSET, LIMIT)
        verify(service, times(1)).fetchAllPokemons(OFFSET, LIMIT)
    }

    @Test
    fun emitPokemonNamesListFromService() = runBlockingTest {
        whenever(pokemonResponse.results).thenReturn(pokemonNames)
        val repository = mockSuccessfulCase()
        assertEquals(pokemonNames, repository.fetchAllPokemons(OFFSET, LIMIT).first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockErrorCase()
        assertEquals(
            backendErrorException,
            repository.fetchAllPokemons(OFFSET, LIMIT).first().exceptionOrNull()
        )
    }

    private suspend fun mockErrorCase(): PokemonListRepositoryImpl {
        whenever(service.fetchAllPokemons(OFFSET, LIMIT)).thenReturn(
            flow {
                emit(Result.failure<PokemonListResponse>(backendErrorException))
            }
        )
        return PokemonListRepositoryImpl(service)
    }

    private suspend fun mockSuccessfulCase(): PokemonListRepositoryImpl {
        whenever(service.fetchAllPokemons(OFFSET, LIMIT))
            .thenReturn(flow {
                emit(Result.success(pokemonResponse))
            })
        return PokemonListRepositoryImpl(service)
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()
        assertEquals(
            backendErrorException,
            service.fetchAllPokemons(OFFSET, LIMIT).first().exceptionOrNull()
        )
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(
            Result.success(pokemonResponse),
            service.fetchAllPokemons(OFFSET, LIMIT).first()
        )
    }

}