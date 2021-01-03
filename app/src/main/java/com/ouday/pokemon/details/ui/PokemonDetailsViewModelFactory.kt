package com.ouday.pokemon.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ouday.pokemon.details.usecase.PokemonDetailsUseCase
import javax.inject.Inject

class PokemonDetailsViewModelFactory @Inject constructor(
    private val useCase: PokemonDetailsUseCase
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonDetailsViewModel(useCase) as T
    }

}
