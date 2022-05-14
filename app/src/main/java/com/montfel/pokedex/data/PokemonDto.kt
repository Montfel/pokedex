package com.montfel.pokedex.data

import com.google.gson.annotations.SerializedName
import com.montfel.pokedex.domain.DtoMapper
import com.montfel.pokedex.domain.Pokemon

data class PokemonDto(
    @SerializedName("height")
    val height: Int,
    @SerializedName("name")
    val name: String
) : DtoMapper<Pokemon> {
    override fun toDomain() = Pokemon(
        height = height,
        name = name.replaceFirstChar { it.uppercase() }
    )
}
