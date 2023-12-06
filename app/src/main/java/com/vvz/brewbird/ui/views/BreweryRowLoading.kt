package com.vvz.brewbird.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.domain.models.BreweryType
import com.vvz.brewbird.ui.theme.BrewbirdTheme


@Composable
fun BreweryRowLoading() {
    BreweryRow(data = BreweryPreview(id = "123",
                                     name = "Lorem ipsum",
                                     city = "Budapest",
                                     country = "Hungary",
                                     type = BreweryType.brewpub),
               isPlaceholder = true,
               isFavourite = false,
               onSelect = {},
               onUpdateFavourite = {})
}


@Preview(showBackground = true)
@Composable
private fun BreweryRowLoadingPreview() {
    BrewbirdTheme {
        BreweryRowLoading()
    }
}