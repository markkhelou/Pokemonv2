package com.ouday.pokemon.pokemonlist

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ouday.pokemon.R
import com.ouday.pokemon.core.launchFragmentInHiltContainer
import com.ouday.pokemon.core.nthChildOf
import com.ouday.pokemon.details.di.PokemonDetailsModule
import com.ouday.pokemon.list.di.PokemonListModule
import com.ouday.pokemon.list.ui.PokemonListFragment
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@UninstallModules(
    PokemonListModule::class,
    PokemonDetailsModule::class)
@HiltAndroidTest
class PokemonListFeature {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun displayPokemonsList() {
        FakePokemonListUseCase.useCaseState = FakePokemonListUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonListFragment>()
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.rvPokemons, 1)
    }

    @Test
    fun showSnackbarWhenFailedToLoadPokemons() {
        FakePokemonListUseCase.useCaseState = FakePokemonListUseCase.UseCaseState.ERROR
        launchFragmentInHiltContainer<PokemonListFragment>()
        BaristaVisibilityAssertions.assertContains("Oops, failed to load pokemons")
    }

    @Test
    fun clickOnSnackbarShouldFetchPokemonsList() {
        FakePokemonListUseCase.useCaseState = FakePokemonListUseCase.UseCaseState.ERROR
        launchFragmentInHiltContainer<PokemonListFragment>()
        FakePokemonListUseCase.useCaseState = FakePokemonListUseCase.UseCaseState.SUCCESS
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withText("Retry")
            )
        )
            .perform(ViewActions.click())
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.rvPokemons, 1)
    }

    @Test
    fun pokemonListShouldShowPokemonName() {
        FakePokemonListUseCase.useCaseState = FakePokemonListUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonListFragment>()
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.pokemon_name),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        withId(R.id.rvPokemons),
                        0
                    )
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText(POKEMON_NAME)))
    }

    @Test
    fun pokemonListShouldShowPokemonNumber() {
        FakePokemonListUseCase.useCaseState = FakePokemonListUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonListFragment>()
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.pokemon_number),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        withId(R.id.rvPokemons),
                        0
                    )
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText("#$POKEMON_ID")))
    }

}