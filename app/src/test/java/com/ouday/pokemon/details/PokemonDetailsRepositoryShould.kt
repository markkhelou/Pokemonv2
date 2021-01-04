package com.ouday.pokemon.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import com.ouday.pokemon.details.data.repo.PokemonDetailsRepositoryImpl
import com.ouday.pokemon.details.data.service.PokemonDetailsService
import com.ouday.pokemon.utils.BaseUnitTest
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

private const val POKEMON_ID = 5

class PokemonDetailsRepositoryShould: BaseUnitTest() {

    private val service: PokemonDetailsService = mock()
    private val pokemonResponse: PokemonDetailsResponse = mock()
    private val backendErrorException = RuntimeException("Backend error")


    @Test
    fun fetchPokemonDetailsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.fetchPokemonDetails(POKEMON_ID)
        verify(service, times(1)).fetchPokemonDetails(POKEMON_ID)
    }

    @Test
    fun emitPokemonDetailsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        TestCase.assertEquals(pokemonResponse, repository.fetchPokemonDetails(POKEMON_ID).first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockErrorCase()
        TestCase.assertEquals(
            backendErrorException,
            repository.fetchPokemonDetails(POKEMON_ID).first().exceptionOrNull()
        )
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()
        TestCase.assertEquals(
            backendErrorException,
            service.fetchPokemonDetails(POKEMON_ID).first().exceptionOrNull()
        )
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()
        TestCase.assertEquals(
            Result.success(pokemonResponse),
            service.fetchPokemonDetails(POKEMON_ID).first()
        )
    }

    private suspend fun mockErrorCase(): PokemonDetailsRepositoryImpl {
        whenever(service.fetchPokemonDetails(POKEMON_ID)).thenReturn(
            flow {
                emit(Result.failure<PokemonDetailsResponse>(backendErrorException))
            }
        )
        return PokemonDetailsRepositoryImpl(
            service
        )
    }

    private suspend fun mockSuccessfulCase(): PokemonDetailsRepositoryImpl {
        whenever(service.fetchPokemonDetails(POKEMON_ID))
            .thenReturn(flow {
                emit(Result.success(pokemonResponse))
            })
        return PokemonDetailsRepositoryImpl(
            service
        )
    }

}