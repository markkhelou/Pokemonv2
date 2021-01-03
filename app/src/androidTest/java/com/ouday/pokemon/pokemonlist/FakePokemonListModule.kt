package com.ouday.pokemon.pokemonlist

import com.ouday.pokemon.list.ui.PokemonListViewModel
import com.ouday.pokemon.list.usecase.PokemonListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FakePokemonListModule {

    @Provides
    fun getPokemonListUseCase(): PokemonListUseCase =
        FakePokemonListUseCase

    @Provides
    fun getPokemonListViewModel(pokemonListUseCase: PokemonListUseCase) =
        PokemonListViewModel(pokemonListUseCase)
}