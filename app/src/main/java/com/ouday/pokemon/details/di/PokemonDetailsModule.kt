package com.ouday.pokemon.details.di

import com.ouday.pokemon.details.data.api.PokemonDetailsApi
import com.ouday.pokemon.details.data.repo.PokemonDetailsRepository
import com.ouday.pokemon.details.data.repo.PokemonDetailsRepositoryImpl
import com.ouday.pokemon.details.data.service.PokemonDetailsService
import com.ouday.pokemon.details.data.service.PokemonDetailsServiceImpl
import com.ouday.pokemon.details.ui.PokemonDetailsViewModel
import com.ouday.pokemon.details.usecase.PokemonDetailsUseCase
import com.ouday.pokemon.details.usecase.PokemonDetailsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class PokemonDetailsModule {

    @Provides
    fun pokemonDetailsAPI(retrofit: Retrofit): PokemonDetailsApi =
        retrofit.create(PokemonDetailsApi::class.java)

    @Provides
    fun getPokemonDetailsService(api: PokemonDetailsApi): PokemonDetailsService =
        PokemonDetailsServiceImpl(api)

    @Provides
    fun getPokemonDetailsRepository(PokemonDetailsService: PokemonDetailsService): PokemonDetailsRepository =
        PokemonDetailsRepositoryImpl(
            PokemonDetailsService
        )

    @Provides
    fun getPokemonDetailsUseCase(PokemonDetailsRepository: PokemonDetailsRepository): PokemonDetailsUseCase =
        PokemonDetailsUseCaseImpl(
            PokemonDetailsRepository
        )

    @Provides
    fun getPokemonDetailsViewModel(PokemonDetailsUseCase: PokemonDetailsUseCase) =
        PokemonDetailsViewModel(PokemonDetailsUseCase)
}