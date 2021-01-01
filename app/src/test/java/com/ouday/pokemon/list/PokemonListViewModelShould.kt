package com.ouday.pokemon.list

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ouday.pokemon.core.Worker
import com.ouday.pokemon.list.model.Pokemon
import com.ouday.pokemon.utils.BaseUnitTest
import com.ouday.pokemon.utils.captureValues
import com.ouday.pokemon.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

private const val OFFSET = 0
private const val LIMIT = 20

class PokemonListViewModelShould : BaseUnitTest() {

    private var useCase: PokemonListUseCase = mock()
    private val pokemon = mock<Pokemon>()
    private val expected = Result.success(arrayListOf(pokemon))
    private val backendErrorException = RuntimeException("Backend error")

    @Test
    fun fetchAllPokemonsFromViewModel() = runBlockingTest {
        mockSuccessfulViewModel().fetchPokemons(OFFSET, LIMIT)
        verify(useCase, times(1)).fetchAllPokemons(OFFSET, LIMIT)
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest{
        val viewModel = mockErrorCase()
        viewModel.fetchPokemons(OFFSET, LIMIT)
        Assert.assertEquals(backendErrorException, viewModel.pokemonsWorker.getValueForTest()?.throwable)
    }

    @Test
    fun emitsPokemonsFromViewModel() = runBlockingTest{
        val viewModel = mockSuccessfulCase()
        viewModel.fetchPokemons(OFFSET, LIMIT)
        Assert.assertEquals(expected.getOrNull(), viewModel.pokemonsWorker.getValueForTest()?.data)
    }

    @Test
    fun emitLoadingStateBeforeEmittingData() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.pokemonsWorker.captureValues {
            viewModel.fetchPokemons(OFFSET, LIMIT)
            Assert.assertEquals(Worker.loading<List<Pokemon>?>(), values[0])
        }
    }

    @Test
    fun emitLoadingStateBeforeEmittingError() = runBlockingTest {
        val viewModel = mockErrorCase()
        viewModel.pokemonsWorker.captureValues {
            viewModel.fetchPokemons(OFFSET, LIMIT)
            Assert.assertEquals(Worker.loading<List<Pokemon>?>(), values[0])
        }
    }

    private fun mockSuccessfulCase(): PokemonListViewModel {
        runBlocking {
            whenever(useCase.fetchAllPokemons(OFFSET, LIMIT)).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return PokemonListViewModel(useCase)
    }

    private suspend fun mockErrorCase(): PokemonListViewModel {
        whenever(useCase.fetchAllPokemons(OFFSET, LIMIT)).thenReturn(
            flow {
                emit(Result.failure(backendErrorException))
            }
        )
        return PokemonListViewModel(useCase)
    }

    suspend fun mockSuccessfulViewModel(): PokemonListViewModel {
        whenever(useCase.fetchAllPokemons(OFFSET, LIMIT)).thenReturn(flow {
            emit(Result.success(arrayListOf(pokemon)))
        })
        return PokemonListViewModel(useCase)
    }

}