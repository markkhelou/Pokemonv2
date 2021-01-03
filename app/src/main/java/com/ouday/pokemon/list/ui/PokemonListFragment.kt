package com.ouday.pokemon.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ouday.pokemon.R
import com.ouday.pokemon.core.Status
import com.ouday.pokemon.databinding.FragmentPokemonListBinding
import com.ouday.pokemon.details.ui.PokemonDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val CHUNK = 40

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private var stopLoadMore: Boolean = false


    @Inject
    lateinit var viewModelFactory: PokemonListViewModelFactory

    @Inject
    lateinit var adapter: PokemonListAdapter

    private var viewModel: PokemonListViewModel? = null

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PokemonListViewModel::class.java)
        setupPokemonRecyclerView()
        viewModel?.pokemonsWorker?.observe(viewLifecycleOwner, { worker ->
            if (worker.status == Status.SUCCESS) {
                worker.data?.let {
                    stopLoadMore = it.size < CHUNK
                    adapter.addPokemons(it)
                }
            } else if (worker.status == Status.ERROR) {
                showRetry()
            }
        })
        loadMorePokemons()
    }

    private fun showRetry() {
        val snackbar =
            Snackbar.make(binding.coordinatorLayout, "Oops, failed to load pokemons", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Retry") {
            loadMorePokemons()
        }
        snackbar.show()
    }

    private fun loadMorePokemons() {
        viewModel?.fetchPokemons(adapter.itemCount, adapter.itemCount + CHUNK)
    }

    private fun setupPokemonRecyclerView() {
        binding.rvPokemons.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPokemons.adapter = adapter
        adapter.onLoadMoreListener =
            { if (!stopLoadMore) loadMorePokemons() }
        adapter.setOnPokemonClicked {
            Navigation.findNavController(requireView())
                .navigate(
                    R.id.action_pokemonListFragment_to_pokemonDetailsFragment,
                    PokemonDetailsFragment.wrapBundle(it.id!!)
                )
        }
    }

}