package com.vvz.brewbird.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vvz.brewbird.R
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.domain.models.BreweryType
import com.vvz.brewbird.ui.views.BreweryFilter
import com.vvz.brewbird.ui.views.BreweryRow
import com.vvz.brewbird.ui.views.BreweryRowLoading
import com.vvz.brewbird.ui.views.ContentError
import com.vvz.brewbird.ui.views.ContentLoading
import com.vvz.brewbird.ui.views.EmptyList

@Composable
fun BreweryList(viewModel: BreweryListViewModel = hiltViewModel(),
                onShowDetails: (brewery: BreweryPreview) -> Unit) {

    val favourites by viewModel.favourites.collectAsState(initial = setOf())
    val items = viewModel.breweries.collectAsLazyPagingItems()

    val state: BreweryListViewModel.State by remember {
        derivedStateOf {
            when (items.loadState.refresh) {
                is LoadState.Error      -> BreweryListViewModel.State.Error
                is LoadState.Loading    -> BreweryListViewModel.State.Loading
                is LoadState.NotLoading -> when (items.itemCount) {
                    0    -> BreweryListViewModel.State.Empty
                    else -> BreweryListViewModel.State.Results
                }
            }
        }
    }

    BreweryListContent(state = state,
                       items = items,
                       favourites = favourites,
                       onSelect = onShowDetails,
                       onToggleFavourite = viewModel::updateFavorite,
                       onReload = viewModel::reload,
                       filter = viewModel.filter,
                       onUpdateFilter = viewModel::updateFilter)

}


@Composable
private fun BreweryListContent(state: BreweryListViewModel.State,
                               items: LazyPagingItems<BreweryPreview>,
                               favourites: Set<String>,
                               onSelect: (brewery: BreweryPreview) -> Unit,
                               onToggleFavourite: (String, Boolean) -> Unit,
                               onReload: () -> Unit,
                               filter: BreweryType?,
                               onUpdateFilter: (BreweryType?) -> Unit) {

    Column {

        BreweryFilter(availableTypes = BreweryType.values().filter { it != BreweryType.unknown },
                      selected = filter,
                      onUpdateSelection = onUpdateFilter)

        Crossfade(targetState = state, label = "list_content", modifier = Modifier.fillMaxSize()) { currentState ->
            when (currentState) {
                BreweryListViewModel.State.Loading -> ContentLoading()
                BreweryListViewModel.State.Empty   -> EmptyList(message = stringResource(id = R.string.content_empty_list))
                BreweryListViewModel.State.Error   -> ContentError(onRetry = onReload)
                BreweryListViewModel.State.Results -> BreweryLazyList(items = items,
                                                                      onSelect = onSelect,
                                                                      favourites = favourites,
                                                                      onToggleFavourite = onToggleFavourite)

            }
        }
    }

}

@Composable
private fun BreweryLazyList(items: LazyPagingItems<BreweryPreview>,
                            onSelect: (brewery: BreweryPreview) -> Unit,
                            favourites: Set<String>,
                            onToggleFavourite: (String, Boolean) -> Unit) {

    LazyColumn {

        items(count = items.itemCount,
              key = items.itemKey { it.id },
              contentType = items.itemContentType { "brewery" }) { index ->

            when (val data = items[index]) {
                null -> BreweryRowLoading()
                else -> BreweryRow(data = data,
                                   onSelect = { onSelect(data) },
                                   isFavourite = favourites.contains(data.id),
                                   onUpdateFavourite = { onToggleFavourite(data.id, it) })
            }

            if (index < items.itemCount - 1) {
                Divider(modifier = Modifier.padding(horizontal = 20.dp))
            }
        }

    }

}