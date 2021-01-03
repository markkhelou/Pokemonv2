package com.ouday.pokemon.pokemondetails

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ouday.pokemon.R
import com.ouday.pokemon.core.launchFragmentInHiltContainer
import com.ouday.pokemon.details.di.PokemonDetailsModule
import com.ouday.pokemon.details.ui.PokemonDetailsFragment
import com.ouday.pokemon.list.di.PokemonListModule
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
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
    PokemonDetailsModule::class,
    PokemonListModule::class
)
@HiltAndroidTest
class PokemonDetailsFeature {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun displayLoading() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        onView(withId(R.id.progressCircleDeterminate)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun displayTypeViewLabel() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        onView(withId(R.id.lblTypes)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun displayMovesViewLabel() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        onView(withId(R.id.lblMoves)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun displayAbilitiesViewLabel() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        onView(withId(R.id.lblAbilities)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun setPokemonTypeViewLabel() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        onView(withId(R.id.lblTypes)).check(matches(ViewMatchers.withText("grass\npoison\n")))
    }


    @Test
    fun setPokemonMovesViewLabel() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        onView(withId(R.id.lblMoves)).check(matches(ViewMatchers.withText("razor wind\nconfide\n")))
    }


    @Test
    fun setPokemonAbilitiesViewLabel() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        onView(withId(R.id.lblAbilities)).check(matches(ViewMatchers.withText("overgrow\nchlorophyll\n")))
    }

    @Test
    fun showSnackbarWhenFailedToLoadPokemonDetails() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.ERROR
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        BaristaVisibilityAssertions.assertContains("Oops, failed to get pokemon details")
    }

    @Test
    fun clickOnSnackbarShouldFetchPokemonDetails() {
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.ERROR
        launchFragmentInHiltContainer<PokemonDetailsFragment>(PokemonDetailsFragment.wrapBundle(1))
        BaristaSleepInteractions.sleep(1500)
        FakePokemonDetailsUseCase.useCaseState = FakePokemonDetailsUseCase.UseCaseState.SUCCESS
        onView(
            CoreMatchers.allOf(
                ViewMatchers.withText("Retry")
            )
        )
            .perform(ViewActions.click())
        BaristaSleepInteractions.sleep(1500)
        onView(withId(R.id.lblTypes)).check(matches(ViewMatchers.withText("grass\npoison\n")))
    }

}
