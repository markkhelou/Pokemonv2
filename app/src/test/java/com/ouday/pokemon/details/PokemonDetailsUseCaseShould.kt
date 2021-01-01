package com.ouday.pokemon.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import com.ouday.pokemon.details.data.repo.PokemonDetailsRepository
import com.ouday.pokemon.utils.BaseUnitTest
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

private const val POKEMON_ID = 5


class PokemonDetailsUseCaseShould: BaseUnitTest() {

    private val repo: PokemonDetailsRepository = mock()
    private val backendErrorException = RuntimeException("Backend error")
    private val pokemonName = mock<PokemonDetailsResponse>()


    @Test
    fun fetchPokemonDetailsFromUseCase() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        useCase.fetchPokemonDetails(POKEMON_ID)
        verify(repo, times(1)).fetchPokemonDetails(POKEMON_ID)
    }

    @Test
    fun emitPokemonsDetailsFromUseCase() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        TestCase.assertEquals(pokemonName, useCase.fetchPokemonDetails(POKEMON_ID).first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val useCase = mockErrorCase()
        TestCase.assertEquals(
            backendErrorException,
            useCase.fetchPokemonDetails(POKEMON_ID).first().exceptionOrNull()
        )
    }

    private suspend fun mockErrorCase(): PokemonDetailsUseCaseImpl {
        whenever(repo.fetchPokemonDetails(POKEMON_ID)).thenReturn(
            flow {
                emit(Result.failure(backendErrorException))
            }
        )
        return PokemonDetailsUseCaseImpl(repo)
    }

    private suspend fun mockSuccessfulCase(): PokemonDetailsUseCaseImpl {
        whenever(repo.fetchPokemonDetails(POKEMON_ID))
            .thenReturn(flow {
                emit(Result.success(pokemonName))
            })
        return PokemonDetailsUseCaseImpl(repo)
    }

}