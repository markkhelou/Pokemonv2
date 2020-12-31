package com.ouday.pokemon.list

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class PokemonListModule {
    @Provides
    fun playlistAPI(retrofit: Retrofit): PokemonListApi = retrofit.create(PokemonListApi::class.java)
}