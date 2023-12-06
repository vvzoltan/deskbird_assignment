package com.vvz.brewbird.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vvz.brewbird.R
import com.vvz.brewbird.domain.models.Address
import com.vvz.brewbird.domain.models.BreweryDetails
import com.vvz.brewbird.domain.models.BreweryType
import com.vvz.brewbird.ui.theme.BrewbirdTheme
import com.vvz.brewbird.ui.views.ContentError
import com.vvz.brewbird.ui.views.ContentLoading


@Composable
fun BreweryDetails(viewModel: BreweryDetailsViewModel = hiltViewModel()) {

    val isFavourite by viewModel.isFavourite.collectAsState(initial = false)

    Crossfade(targetState = viewModel.state, label = "details_content", modifier = Modifier.fillMaxSize()) { state ->
        when (state) {
            is BreweryDetailsViewModel.State.Loading -> ContentLoading()
            is BreweryDetailsViewModel.State.Loaded  -> BreweryDetailsContent(breweryDetails = state.details,
                                                                              isFavourite = isFavourite,
                                                                              onToggleFavourite = viewModel::updateFavourite)

            is BreweryDetailsViewModel.State.Error   -> ContentError()
        }
    }

}


@Composable
private fun BreweryDetailsContent(breweryDetails: BreweryDetails,
                                  isFavourite: Boolean,
                                  onToggleFavourite: (Boolean) -> Unit) {

    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(20.dp),
           modifier = Modifier
               .fillMaxSize()
               .verticalScroll(state = rememberScrollState())
               .padding(20.dp)) {

        Column {
            Text(text = breweryDetails.name,
                 style = MaterialTheme.typography.headlineMedium,
                 textAlign = TextAlign.Center,
                 modifier = Modifier.fillMaxWidth())

            Text(text = breweryDetails.type.name,
                 style = MaterialTheme.typography.labelLarge,
                 textAlign = TextAlign.Center,
                 modifier = Modifier
                     .fillMaxWidth()
                     .alpha(0.7f))
        }

        Column {
            Text(text = stringResource(id = R.string.details_address),
                 style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            breweryDetails.address.street?.let { street ->
                Text(text = street)
            }
            Text(text = stringResource(id = R.string.details_address_format,
                                       breweryDetails.address.postCode,
                                       breweryDetails.address.city,
                                       breweryDetails.address.state))
            Text(text = breweryDetails.address.country)
        }

        Button(onClick = {
            val latitude = checkNotNull(breweryDetails.address.latitude)
            val longitude = checkNotNull(breweryDetails.address.longitude)
            val uri: String = "geo:%f,%f".format(latitude, longitude)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context.startActivity(intent)
        },
               modifier = Modifier.fillMaxWidth(),
               enabled = breweryDetails.address.latitude != null && breweryDetails.address.longitude != null) {
            Text(text = stringResource(id = R.string.details_button_map))
        }

        Button(onClick = {
            var url = checkNotNull(breweryDetails.website)
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        },
               modifier = Modifier.fillMaxWidth(),
               enabled = breweryDetails.website != null) {
            Text(text = stringResource(id = R.string.details_button_web))
        }

        Button(onClick = {
            val number = checkNotNull(breweryDetails.phone)
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            context.startActivity(intent)
        },
               modifier = Modifier.fillMaxWidth(),
               enabled = breweryDetails.phone != null) {
            Text(text = stringResource(id = R.string.details_button_call))
        }

        Crossfade(targetState = isFavourite,
                  label = "favourite_button",
                  modifier = Modifier.fillMaxWidth()) { isFavourite ->

            when (isFavourite) {
                true  -> OutlinedButton(onClick = { onToggleFavourite(false) },
                                        content = { Text(text = stringResource(id = R.string.button_remove_favourite)) },
                                        modifier = Modifier.fillMaxWidth())

                false -> Button(onClick = { onToggleFavourite(true) },
                                content = { Text(text = stringResource(id = R.string.button_add_favourite)) },
                                modifier = Modifier.fillMaxWidth())
            }
        }

    }

}


@Preview(showSystemUi = true)
@Composable
private fun BreweryDetailsContentPreview() {
    BrewbirdTheme {
        val details = BreweryDetails(id = "",
                                     name = "Lolek",
                                     address = Address(street = "1 Main street",
                                                       address1 = null,
                                                       address2 = null,
                                                       address3 = null,
                                                       city = "Budapest",
                                                       state = "Pest",
                                                       postCode = "1139",
                                                       country = "Hungary",
                                                       longitude = null,
                                                       latitude = null),
                                     type = BreweryType.micro,
                                     phone = "5558881234",
                                     website = "http://google.com")
        BreweryDetailsContent(breweryDetails = details,
                              isFavourite = true,
                              onToggleFavourite = {})
    }
}