package com.ouday.pokemon.list.di

import com.ouday.pokemon.list.ui.PokemonListViewModel
import com.ouday.pokemon.list.data.api.PokemonListApi
import com.ouday.pokemon.list.data.repo.PokemonListRepository
import com.ouday.pokemon.list.data.repo.PokemonListRepositoryImpl
import com.ouday.pokemon.list.data.service.PokemonListService
import com.ouday.pokemon.list.data.service.PokemonListServiceImpl
import com.ouday.pokemon.list.usecase.PokemonListUseCase
import com.ouday.pokemon.list.usecase.PokemonListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class PokemonListModule {

    @Provides
    fun playlistAPI(retrofit: Retrofit): PokemonListApi =
        retrofit.create(PokemonListApi::class.java)

    @Provides
    fun getPokemonListService(api: PokemonListApi): PokemonListService =
        PokemonListServiceImpl(api)

    @Provides
    fun getPokemonListRepository(pokemonListService: PokemonListService): PokemonListRepository =
        PokemonListRepositoryImpl(
            pokemonListService
        )

    @Provides
    fun getPokemonListUseCase(pokemonListRepository: PokemonListRepository): PokemonListUseCase =
        PokemonListUseCaseImpl(
            pokemonListRepository
        )

    @Provides
    fun getPokemonListViewModel(pokemonListUseCase: PokemonListUseCase) =
        PokemonListViewModel(pokemonListUseCase)
}