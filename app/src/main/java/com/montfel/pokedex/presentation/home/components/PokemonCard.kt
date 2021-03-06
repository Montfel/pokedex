package com.montfel.pokedex.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.montfel.pokedex.R
import com.montfel.pokedex.domain.model.PokemonHome
import com.montfel.pokedex.presentation.components.TypeCard
import com.montfel.pokedex.presentation.theme.*

@Composable
fun PokemonCard(
    pokemon: PokemonHome,
    onClick: () -> Unit
) {
    val assetHelper = LocalAssetHelper.current
    val mainType = pokemon.types.firstOrNull { type -> type.slot == 1 }
    val assetBackground = assetHelper.getAsset(mainType?.type?.name ?: "")

    Spacer(modifier = Modifier.height(4.dp))

    Box(
        modifier = Modifier
            .height(140.dp)
            .clipToBounds()
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = assetBackground.backgroundColor,
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .height(115.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "#${pokemon.id}",
                    style = MaterialTheme.typography.pokemonNumber,
                    color = MaterialTheme.colors.numberOverBackgroundColor
                )
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.pokemonName,
                    color = MaterialTheme.colors.secondaryText,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    pokemon.types.forEach {
                        TypeCard(typeName = it.type.name)
                    }
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = null,
            alpha = 0.3f,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(140.dp)
                .offset(x = 15.dp, y = 15.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_6x3),
            contentDescription = null,
            alpha = 0.3f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-36).dp, y = (-18).dp)
                .width(74.dp)
                .height(32.dp)
        )
        AsyncImage(
            model = stringResource(id = R.string.pokemon_image_url, pokemon.id),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 10.dp)
                .size(130.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonCardPreview() {
    PokemonCard(
        pokemon = PokemonHome(
            id = 1,
            name = "Bulbasaur",
            image = "",
            types = emptyList()
        )
    ) {}
}
