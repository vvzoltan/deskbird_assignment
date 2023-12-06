package com.vvz.brewbird.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vvz.brewbird.R
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.ui.views.BreweryRow
import com.vvz.brewbird.ui.views.ContentError
import com.vvz.brewbird.ui.views.ContentLoading
import com.vvz.brewbird.ui.views.EmptyList

@Composable
fun FavouritesList(viewModel: FavouritesListViewModel = hiltViewModel(),
                   onShowDetails: (brewery: BreweryPreview) -> Unit) {


    Crossfade(targetState = viewModel.state, label = "list_content", modifier = Modifier.fillMaxSize()) { currentState ->
        when (currentState) {
            is FavouritesListViewModel.State.Loading -> ContentLoading()
            is FavouritesListViewModel.State.Empty   -> EmptyList(message = stringResource(id = R.string.content_empty_favourites))
            is FavouritesListViewModel.State.Error   -> ContentError(onRetry = viewModel::loadList)
            is FavouritesListViewModel.State.Result  -> FavouritesContent(items = currentState.items,
                                                                          favourites = viewModel.favourites,
                                                                          onSelect = onShowDetails,
                                                                          onRemoveFavourite = viewModel::removeFavourite)

        }
    }

}


@Composable
private fun FavouritesContent(items: List<BreweryPreview>,
                              favourites: Set<String>,
                              onSelect: (BreweryPreview) -> Unit,
                              onRemoveFavourite: (String) -> Unit) {
    LazyColumn {

        itemsIndexed(items) { index, preview ->
            BreweryRow(data = preview,
                       onSelect = { onSelect(preview) },
                       isFavourite = favourites.contains(preview.id),
                       onUpdateFavourite = { onRemoveFavourite(preview.id) })

            if (index < items.lastIndex) {
                Divider(modifier = Modifier.padding(horizontal = 20.dp))
            }
        }

    }
}