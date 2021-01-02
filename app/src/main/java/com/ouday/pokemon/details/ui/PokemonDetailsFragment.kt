package com.ouday.pokemon.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ouday.pokemon.R
import com.ouday.pokemon.core.Status
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import kotlinx.android.synthetic.main.fragment_pokemon_details_abilities.*
import kotlinx.android.synthetic.main.fragment_pokemon_details_category.*
import kotlinx.android.synthetic.main.fragment_pokemon_details_moves.*
import java.lang.StringBuilder
import javax.inject.Inject


private const val BUNDLE_KEY_POKEMON_ID = "BUNDLE_KEY_POKEMON_ID"

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

    @Inject
    lateinit var viewModelFactory: PokemonDetailsViewModelFactory

    private lateinit var viewModel: PokemonDetailsViewModel
    private var pokemonID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonID = requireArguments().getInt(BUNDLE_KEY_POKEMON_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PokemonDetailsViewModel::class.java)
        viewModel.pokemonDetailsWorker.observe(viewLifecycleOwner, Observer { worker ->
            when (worker.status) {
                Status.SUCCESS -> binData(worker.data)
                Status.LOADING -> showLoader()
                Status.ERROR -> showError()
            }
        })
        viewModel.fetchPokemonDetails(pokemonID)
    }

    private fun showLoader() {
        progressCircleDeterminate.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    private fun showError() {
        Snackbar
            .make(coordinatorLayout, "Oops, failed to get pokemon details", Snackbar.LENGTH_LONG)
            .show()
    }

    private fun binData(pokemonDetails: PokemonDetailsResponse?) {
        progressCircleDeterminate.visibility = View.GONE
        content.visibility = View.VISIBLE
        val pokemonAbilities = StringBuilder()
        val pokemonTypes = StringBuilder()
        val pokemonMoves = StringBuilder()
        pokemonDetails?.apply {
            abilities.forEach {
                pokemonAbilities.append("${it.ability.name.replace('-', ' ')}\n")
            }
            types.forEach {
                pokemonTypes.append("${it.type.name.replace('-', ' ')}\n")
            }
            moves.forEach {
                pokemonMoves.append("${it.move.name.replace('-', ' ')}\n")
            }
        }
        lblAbilities.text = pokemonAbilities.toString()
        lblTypes.text = pokemonTypes.toString()
        lblMoves.text = pokemonMoves.toString()
    }

    companion object {
        fun wrapBundle(pokemonID: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(BUNDLE_KEY_POKEMON_ID, pokemonID)
            return bundle
        }
    }

}