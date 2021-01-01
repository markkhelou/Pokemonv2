package com.ouday.pokemon.details

import com.ouday.pokemon.details.response.*

data class PokemonDetailsResponse(
    val damage_relations: DamageRelations,
    val game_indices: List<GameIndice>,
    val generation: Generation,
    val id: Int,
    val move_damage_class: MoveDamageClass,
    val moves: List<Move>,
    val name: String,
    val names: List<Name>
)