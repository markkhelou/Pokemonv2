package com.ouday.pokemon.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ouday.pokemon.details.data.api.PokemonDetailsApi
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import com.ouday.pokemon.details.data.service.PokemonDetailsServiceImpl
import com.ouday.pokemon.utils.BaseUnitTest
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

private const val POKEMON_ID = 5

class PokemonDetailsServiceShould : BaseUnitTest(){

    private lateinit var service: PokemonDetailsServiceImpl
    private val api: PokemonDetailsApi = mock()
    private val pokemonResponse: PokemonDetailsResponse = mock()
    private val backendErrorException = RuntimeException("Backend error")

    @Test
    fun fetchPokemonDetailsFromApi() = runBlockingTest {
        service =
            PokemonDetailsServiceImpl(api)
        service.fetchPokemonDetails(POKEMON_ID).first()
        verify(api, times(1)).fetchPokemonDetailsAsync(POKEMON_ID)
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()
        TestCase.assertEquals(
            Result.success(pokemonResponse),
            service.fetchPokemonDetails(POKEMON_ID).first()
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

    private suspend fun mockErrorCase() {
        whenever(api.fetchPokemonDetailsAsync(POKEMON_ID))
            .thenThrow(backendErrorException)
        service =
            PokemonDetailsServiceImpl(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchPokemonDetailsAsync(POKEMON_ID))
            .thenReturn(pokemonResponse)
        service =
            PokemonDetailsServiceImpl(api)
    }

}