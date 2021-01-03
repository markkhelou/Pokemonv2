package com.ouday.pokemon.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ouday.pokemon.list.usecase.PokemonListUseCase
import javax.inject.Inject

class PokemonListViewModelFactory @Inject constructor(
    private val useCase: PokemonListUseCase
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonListViewModel(useCase) as T
    }

}
