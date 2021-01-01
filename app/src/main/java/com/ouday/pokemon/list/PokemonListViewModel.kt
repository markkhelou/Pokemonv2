package com.ouday.pokemon.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ouday.pokemon.core.Worker
import com.ouday.pokemon.list.model.Pokemon
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonListViewModel @Inject constructor(
    private val useCase: PokemonListUseCase
) : ViewModel() {

    var pokemonsWorker = MediatorLiveData<Worker<List<Pokemon>?>>()

    fun fetchPokemons(offset: Int, limit: Int) {
        pokemonsWorker.postValue(Worker.loading())
        viewModelScope.launch {
            useCase.fetchAllPokemons(offset, limit)
                .collect {
                    if (it.isSuccess) {
                        pokemonsWorker.postValue(Worker.success(it.getOrNull()))
                    } else {
                        pokemonsWorker.postValue(Worker.error(it.exceptionOrNull()))
                    }
                }
        }
    }

}