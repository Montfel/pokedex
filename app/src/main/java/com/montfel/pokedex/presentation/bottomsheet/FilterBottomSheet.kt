package com.montfel.pokedex.presentation.bottomsheet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.montfel.pokedex.R
import com.montfel.pokedex.helper.Asset
import com.montfel.pokedex.presentation.components.BottomSheetHeader
import com.montfel.pokedex.presentation.components.FilterItem
import com.montfel.pokedex.presentation.theme.*

@Composable
fun FilterBottomSheet(assetList: List<Asset>) {
    val heightList = listOf(
        Asset(
            typeColor = HeightShort,
            icon = R.drawable.ic_resource_short,
            backgroundColor = Color.White
        ),
        Asset(
            typeColor = HeightMedium,
            icon = R.drawable.ic_medium,
            backgroundColor = Color.White
        ),
        Asset(
            typeColor = HeightTall,
            icon = R.drawable.ic_tall,
            backgroundColor = Color.White
        )
    )

    val weightList = listOf(
        Asset(
            typeColor = WeightLight,
            icon = R.drawable.ic_light,
            backgroundColor = Color.White
        ),
        Asset(
            typeColor = WeightNormal,
            icon = R.drawable.ic_normal,
            backgroundColor = Color.White
        ),
        Asset(
            typeColor = WeightHeavy,
            icon = R.drawable.ic_heavy,
            backgroundColor = Color.White
        )
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BottomSheetHeader(
            title = R.string.filters,
            description = R.string.filters_description,
            Modifier.padding(horizontal = 16.dp)
        )

        FilterSection(
            title = R.string.types,
            items = assetList
        )

        FilterSection(
            title = R.string.weakeness,
            items = assetList
        )

        FilterSection(
            title = R.string.heights,
            items = heightList
        )

        FilterSection(
            title = R.string.weights,
            items = weightList
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondaryInput,
                    contentColor = MaterialTheme.colors.secondaryVariantInput
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.reset),
                    style = MaterialTheme.typography.description,
                    color = MaterialTheme.colors.primaryVariantText
                )
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryInput,
                    contentColor = MaterialTheme.colors.secondaryText
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.apply),
                    style = MaterialTheme.typography.description
                )
            }
        }
        Spacer(modifier = Modifier.height(1.dp))
    }
}

@Composable
fun FilterSection(
    @StringRes title: Int,
    items: List<Asset>
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.filterTitle,
            color = MaterialTheme.colors.primaryText,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items) {
                FilterItem(icon = it.icon, typeColor = it.typeColor)
            }
        }
    }
}
