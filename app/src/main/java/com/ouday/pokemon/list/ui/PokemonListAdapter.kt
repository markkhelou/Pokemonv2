package com.ouday.pokemon.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ouday.pokemon.R
import com.ouday.pokemon.databinding.ViewholderPokemonListBinding
import com.ouday.pokemon.list.data.model.Pokemon
import javax.inject.Inject

class PokemonListAdapter @Inject constructor() :
    RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private val data = ArrayList<Pokemon>()
    private var onPokemonClicked: ((namedResponseModel: Pokemon) -> Any)? = null

    var onLoadMoreListener: (()->Unit)? = null

    fun setOnPokemonClicked(onPokemonClicked: ((namedResponseModel: Pokemon) -> Any)): PokemonListAdapter {
        this.onPokemonClicked = onPokemonClicked
        return this
    }

    fun addPokemons(pokemons: List<Pokemon>){
        data.addAll(pokemons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ViewholderPokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(pokemonViewHolder: PokemonViewHolder, position: Int) {
        pokemonViewHolder.bind(data[position])
        if (position+1 == itemCount) onLoadMoreListener?.invoke()
    }

    override fun getItemCount() = data.size

    inner class PokemonViewHolder(private val binding: ViewholderPokemonListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonName.text = pokemon.name
            binding.pokemonNumber.text = "#${pokemon.id}"
            Glide
                .with(itemView.context)
                .load(pokemon.pokemonImage)
                .placeholder(R.drawable.ic_pokeball)
                .into(binding.imageFront)
            itemView.setOnClickListener { onPokemonClicked?.invoke(pokemon) }
        }
    }

}

