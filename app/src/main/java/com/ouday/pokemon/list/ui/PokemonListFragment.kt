package com.ouday.pokemon.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.ouday.pokemon.R
import com.ouday.pokemon.core.Status
import com.ouday.pokemon.details.ui.PokemonDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import javax.inject.Inject

private const val CHUNK = 40
private const val MAX_NUMBER_OF_POKEMONS = 1118

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    @Inject
    lateinit var viewModelFactory: PokemonListViewModelFactory

    @Inject
    lateinit var adapter: PokemonListAdapter

    private var viewModel: PokemonListViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PokemonListViewModel::class.java)
        setupPokemonRecyclerView()
        viewModel?.pokemonsWorker?.observe(viewLifecycleOwner, Observer { worker ->
            if (worker.status == Status.SUCCESS) {
                worker.data?.let { adapter.addPokemons(it) }
            }
        })
        loadMorePokemons()
    }

    private fun loadMorePokemons() {
        viewModel?.fetchPokemons(adapter.itemCount, adapter.itemCount + CHUNK)
    }

    private fun setupPokemonRecyclerView() {
        rvPokemons.layoutManager = GridLayoutManager(requireContext(), 2)
        rvPokemons.adapter = adapter
        adapter.onLoadMoreListener =
            { if (adapter.itemCount < MAX_NUMBER_OF_POKEMONS) loadMorePokemons() }
        adapter.setOnPokemonClicked {
            Navigation.findNavController(requireView())
                .navigate(
                    R.id.action_pokemonListFragment_to_pokemonDetailsFragment,
                    PokemonDetailsFragment.wrapBundle(it.id!!)
                )
        }
    }

}