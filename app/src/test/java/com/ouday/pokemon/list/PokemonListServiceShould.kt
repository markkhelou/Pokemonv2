package com.ouday.pokemon.list

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import com.ouday.pokemon.list.model.response.PokemonListResponse
import com.ouday.pokemon.utils.BaseUnitTest

private const val OFFSET = 0
private const val LIMIT = 20

class PokemonListServiceShould: BaseUnitTest() {

    private lateinit var service: PokemonListServiceImpl
    private val api: PokemonListApi = mock()
    private val pokemonResponse: PokemonListResponse = mock()
    private val backendErrorException = RuntimeException("Backend error")

    @Test
    fun fetchPlaylistsFromAPI() = runBlockingTest {
        service = PokemonListServiceImpl(api)
        service.fetchAllPokemons(OFFSET, LIMIT).first()
        verify(api, times(1)).fetchAllPokemons(OFFSET, LIMIT)
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchAllPokemons(OFFSET, LIMIT))
            .thenThrow(backendErrorException)
        service = PokemonListServiceImpl(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPokemons(OFFSET, LIMIT))
            .thenReturn(pokemonResponse)
        service = PokemonListServiceImpl(api)
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