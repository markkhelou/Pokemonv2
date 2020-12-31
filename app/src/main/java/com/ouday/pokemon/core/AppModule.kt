package com.ouday.pokemon.core

import com.ouday.pokemon.list.PokemonListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val client = OkHttpClient()
private const val URL_POKEMON = "https://pokeapi.co/api/v2"

@Module
@InstallIn(FragmentComponent::class)
class AppModule {

    @Provides
    fun playlistAPI(retrofit: Retrofit) = retrofit.create(PokemonListApi::class.java)

    @Provides
    fun retrofit() = Retrofit.Builder()
        .baseUrl(URL_POKEMON)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}