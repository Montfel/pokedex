package com.montfel.pokedex.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.montfel.pokedex.R
import com.montfel.pokedex.presentation.bottomsheet.FilterBottomSheet
import com.montfel.pokedex.presentation.bottomsheet.GenerationBottomSheet
import com.montfel.pokedex.presentation.bottomsheet.SortBottomSheet
import com.montfel.pokedex.presentation.bottomsheet.SortOptions
import com.montfel.pokedex.presentation.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class BottomSheetFilter {
    Generation, Sort, Filter
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val assetHelper = LocalAssetHelper.current
    val gradientColors = listOf(GrayF5, Color.White) //TODO: change to MaterialTheme.colors
    var text by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf(BottomSheetFilter.Filter) }
    var generationSelected by remember { mutableStateOf("") }
    var sortSelectedOption by remember { mutableStateOf(SortOptions.SmallestNumber) }
//    var typesSelected = remember { mutableStateListOf<Int>() }
//    var weaknessesSelected = remember { mutableStateListOf<Int>() }
//    var heightsSelected = remember { mutableStateListOf<Int>() }
//    var weightsSelected = remember { mutableStateListOf<Int>() }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.showAllPokemons()
        viewModel.saveAllTypes(assetHelper)
        viewModel.saveAllGenerations()
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            when (filter) {
                BottomSheetFilter.Generation -> GenerationBottomSheet(
                    generationList = uiState.generationList ?: emptyList(),
                    generationSelected = generationSelected,
                    onGenerationSelected = { generation ->
                        generationSelected = generation.name
                        viewModel.filterByGeneration(generation)
                        scope.launch(Dispatchers.Main) {
                            sheetState.hide()
                        }
                    }
                )
                BottomSheetFilter.Sort -> SortBottomSheet(
                    sortSelectedOption = sortSelectedOption,
                    onSortOptionSelected = { sortOption ->
                        sortSelectedOption = sortOption
                        viewModel.sortPokemons(sortOption)
                        scope.launch(Dispatchers.Main) {
                            sheetState.hide()
                        }
                    }
                )
                BottomSheetFilter.Filter -> FilterBottomSheet(
                    assetList = uiState.assetList ?: emptyList(),
                    onFilterApplied = {
//                        viewModel.filterByAsset(it)
                        scope.launch(Dispatchers.Main) {
                            sheetState.hide()
                        }
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pokeball),
                contentDescription = null,
                colorFilter = ColorFilter.tint(GrayF5), //TODO: change to MaterialTheme.colors
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .align(Alignment.TopCenter)
                    .offset(y = (-205).dp) //TODO: fix this
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .offset(y = (-205).dp) //TODO: fix this
                    .background(
                        brush = Brush.verticalGradient(gradientColors),
                        alpha = 0.1f
                    )
            )
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                item {
                    TopBar(
                        onClick = {
                            filter = it
                            scope.launch(Dispatchers.Main) {
                                sheetState.show()
                            }
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.applicationTitle,
                        color = MaterialTheme.colors.primaryText,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.subtitle),
                        style = MaterialTheme.typography.description,
                        color = MaterialTheme.colors.primaryVariantText,
                    )
                    SearchField(
                        text = text,
                        onType = {
                            text = it
                            viewModel.searchPokemon(it)
                        }
                    )
                }
                uiState.pokemonList?.let { pokemonList ->
                    items(
                        items = pokemonList,
                        key = { it.id }
                    ) { pokemon ->
                        PokemonCard(pokemon = pokemon) {
                            navController.navigate("profile/${pokemon.id}")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchField(
    text: String,
    onType: (String) -> Unit,
) {
    TextField(
        value = text,
        onValueChange = { onType(it) },
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        textStyle = MaterialTheme.typography.description,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
        },
        colors = textFieldColors(
            backgroundColor = MaterialTheme.colors.secondaryInput,
            textColor = MaterialTheme.colors.primaryText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.primaryInput,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.placeholder_textfield),
                style = MaterialTheme.typography.description,
                color = MaterialTheme.colors.primaryVariantText
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    )
}
