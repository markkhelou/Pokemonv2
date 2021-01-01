package com.ouday.pokemon.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ouday.pokemon.core.Worker
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import com.ouday.pokemon.details.ui.PokemonDetailsViewModel
import com.ouday.pokemon.details.usecase.PokemonDetailsUseCase
import com.ouday.pokemon.utils.BaseUnitTest
import com.ouday.pokemon.utils.captureValues
import com.ouday.pokemon.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

private const val POKEMON_ID = 5

class PokemonDetailsViewModelShould : BaseUnitTest() {

    private var useCase: PokemonDetailsUseCase = mock()
    private val pokemon = mock<PokemonDetailsResponse>()
    private val expected = Result.success(pokemon)
    private val backendErrorException = RuntimeException("Backend error")

    @Test
    fun fetchAllPokemonsFromViewModel() = runBlockingTest {
        mockSuccessfulViewModel().fetchPokemonDetails(POKEMON_ID)
        verify(useCase, times(1)).fetchPokemonDetails(POKEMON_ID)
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockErrorCase()
        viewModel.fetchPokemonDetails(POKEMON_ID)
        Assert.assertEquals(
            backendErrorException,
            viewModel.pokemonDetailsWorker.getValueForTest()?.throwable
        )
    }

    @Test
    fun emitsPokemonsFromViewModel() = runBlockingTest {
        val viewModel = mockSuccessfulViewModel()
        viewModel.fetchPokemonDetails(POKEMON_ID)
        Assert.assertEquals(
            expected.getOrNull(),
            viewModel.pokemonDetailsWorker.getValueForTest()?.data
        )
    }

    @Test
    fun emitLoadingStateBeforeEmittingData() = runBlockingTest {
        val viewModel = mockSuccessfulViewModel()
        viewModel.pokemonDetailsWorker.captureValues {
            viewModel.fetchPokemonDetails(POKEMON_ID)
            Assert.assertEquals(Worker.loading<PokemonDetailsResponse?>(), values[0])
        }
    }

    @Test
    fun emitLoadingStateBeforeEmittingError() = runBlockingTest {
        val viewModel = mockErrorCase()
        viewModel.pokemonDetailsWorker.captureValues {
            viewModel.fetchPokemonDetails(POKEMON_ID)
            Assert.assertEquals(Worker.loading<PokemonDetailsResponse?>(), values[0])
        }
    }

    private suspend fun mockErrorCase(): PokemonDetailsViewModel {
        whenever(useCase.fetchPokemonDetails(POKEMON_ID)).thenReturn(
            flow {
                emit(Result.failure(backendErrorException))
            }
        )
        return PokemonDetailsViewModel(useCase)
    }

    suspend fun mockSuccessfulViewModel(): PokemonDetailsViewModel {
        whenever(useCase.fetchPokemonDetails(POKEMON_ID)).thenReturn(
            flow {
                emit(Result.success(pokemon))
            })
        return PokemonDetailsViewModel(useCase)
    }

}