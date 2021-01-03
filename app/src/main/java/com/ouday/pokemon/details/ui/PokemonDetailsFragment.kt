package com.ouday.pokemon.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ouday.pokemon.R
import com.ouday.pokemon.core.Status
import com.ouday.pokemon.databinding.FragmentPokemonDetailsBinding
import com.ouday.pokemon.details.data.model.response.PokemonDetailsResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val BUNDLE_KEY_POKEMON_ID = "BUNDLE_KEY_POKEMON_ID"

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

    private lateinit var viewModel: PokemonDetailsViewModel
    private var pokemonID: Int = -1

    @Inject
    lateinit var viewModelFactory: PokemonDetailsViewModelFactory

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonID = requireArguments().getInt(BUNDLE_KEY_POKEMON_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PokemonDetailsViewModel::class.java)
        viewModel.pokemonDetailsWorker.observe(viewLifecycleOwner, { worker ->
            when (worker.status) {
                Status.SUCCESS -> binData(worker.data)
                Status.LOADING -> showLoader()
                Status.ERROR -> showError()
            }
        })
        viewModel.fetchPokemonDetails(pokemonID)
    }

    private fun showLoader() {
        binding.progressCircleDeterminate.visibility = View.VISIBLE
        binding.content.visibility = View.GONE
    }

    private fun showError() {
        val snackbar =
            Snackbar.make(binding.coordinatorLayout, "Oops, failed to get pokemon details", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Retry") {
            viewModel.fetchPokemonDetails(pokemonID)
        }
        snackbar.show()
    }

    private fun binData(pokemonDetails: PokemonDetailsResponse?) {
        binding.progressCircleDeterminate.visibility = View.GONE
        binding.content.visibility = View.VISIBLE
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
        binding.lblAbilities.text = pokemonAbilities.toString()
        binding.lblTypes.text = pokemonTypes.toString()
        binding.lblMoves.text = pokemonMoves.toString()
    }

    companion object {
        fun wrapBundle(pokemonID: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(BUNDLE_KEY_POKEMON_ID, pokemonID)
            return bundle
        }
    }

}