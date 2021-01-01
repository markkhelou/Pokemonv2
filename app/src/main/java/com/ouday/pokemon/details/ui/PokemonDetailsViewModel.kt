package com.ouday.pokemon.details.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ouday.pokemon.core.Worker
import com.ouday.pokemon.details.PokemonDetailsUseCase
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonDetailsViewModel @Inject constructor(private val useCase: PokemonDetailsUseCase) :
    ViewModel() {

    var pokemonDetailsWorker = MediatorLiveData<Worker<PokemonDetailsResponse?>>()

    fun fetchPokemonDetails(pokemonID: Int) {
        pokemonDetailsWorker.postValue(Worker.loading())
        viewModelScope.launch {
            useCase.fetchPokemonDetails(pokemonID)
                .collect {
                    if (it.isSuccess) {
                        pokemonDetailsWorker.postValue(Worker.success(it.getOrNull()))
                    } else {
                        pokemonDetailsWorker.postValue(Worker.error(it.exceptionOrNull()))
                    }
                }
        }
    }

}