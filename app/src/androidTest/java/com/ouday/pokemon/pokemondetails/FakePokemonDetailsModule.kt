package com.ouday.pokemon.pokemondetails

import com.ouday.pokemon.details.ui.PokemonDetailsViewModel
import com.ouday.pokemon.details.usecase.PokemonDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FakePokemonDetailsModule {

    @Provides
    fun getPokemonDetailsUseCase(): PokemonDetailsUseCase = FakePokemonDetailsUseCase

    @Provides
    fun getPokemonDetailsViewModel(pokemonDetailsUseCase: PokemonDetailsUseCase): PokemonDetailsViewModel =
        PokemonDetailsViewModel(pokemonDetailsUseCase)
}