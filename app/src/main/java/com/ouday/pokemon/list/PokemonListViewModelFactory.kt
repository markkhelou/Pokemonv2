package com.ouday.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PokemonListViewModelFactory @Inject constructor(
    private val useCase: PokemonListUseCase
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonListViewModel(useCase) as T
    }

}
