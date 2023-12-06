package com.vvz.brewbird.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.domain.models.BreweryType
import com.vvz.brewbird.ui.theme.BrewbirdTheme


@Composable
fun BreweryRow(data: BreweryPreview,
               isFavourite: Boolean,
               isPlaceholder: Boolean = false,
               onSelect: () -> Unit,
               onUpdateFavourite: (Boolean) -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable(enabled = !isPlaceholder, onClick = onSelect)) {

        Column(horizontalAlignment = Alignment.Start,
               modifier = Modifier
                   .weight(1f)
                   .padding(horizontal = 20.dp, vertical = 8.dp)) {
            Text(data.name, maxLines = 1, modifier = Modifier.placeholder(isPlaceholder))
            Text("%s, %s".format(data.city, data.country), modifier = Modifier.placeholder(isPlaceholder))
            Text(text = data.type.name, maxLines = 1, modifier = Modifier
                .padding(top = 4.dp)
                .placeholder(isPlaceholder)
                .alpha(0.5f))
        }

        Box(modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clickable(enabled = !isPlaceholder, onClick = { onUpdateFavourite(!isFavourite) })) {

            Icon(imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                 contentDescription = null,
                 tint = if (isFavourite) Color.Red else Color.Gray,
                 modifier = Modifier
                     .size(30.dp)
                     .align(Alignment.Center)
                     .placeholder(isPlaceholder))
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun BreweryRowPreview() {
    BrewbirdTheme {
        BreweryRow(data = BreweryPreview(id = "123", name = "Lorem ipsum", city = "Budapest", "Hungary", BreweryType.brewpub),
                   isFavourite = false,
                   onSelect = {},
                   onUpdateFavourite = {})
    }
}


@Preview(showBackground = true)
@Composable
private fun BreweryRowPlaceholderPreview() {
    BrewbirdTheme {
        BreweryRow(data = BreweryPreview(id = "123", name = "Lorem ipsum", city = "Budapest", "Hungary", BreweryType.brewpub),
                   isPlaceholder = true,
                   isFavourite = false,
                   onSelect = {},
                   onUpdateFavourite = {})
    }
}