package com.ouday.pokemon.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ouday.pokemon.R
import com.ouday.pokemon.list.model.Pokemon
import kotlinx.android.synthetic.main.viewholder_pokemon_list.view.*
import javax.inject.Inject

class PokemonListAdapter @Inject constructor() :
    RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private val data = ArrayList<Pokemon>()
    private var onPokemonClicked: ((namedResponseModel: Pokemon) -> Any)? = null

    var onLoadMoreListener: (()->Unit)? = null
    var isLoadMoreEnabled = true

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
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_pokemon_list, null, false)
        )
    }

    override fun onBindViewHolder(pokemonViewHolder: PokemonViewHolder, position: Int) {
        pokemonViewHolder.bind(data[position])
        if (position+1 == itemCount) onLoadMoreListener?.invoke()
    }

    override fun getItemCount() = data.size

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pokemon: Pokemon) {
            itemView.pokemon_name.text = pokemon.name
            itemView.pokemon_number.text = "#${pokemon.id}"
            Glide
                .with(itemView.context)
                .load(pokemon.pokemonImage)
                .placeholder(R.drawable.ic_pokeball)
                .into(itemView.imageFront)
        }
    }

}

