package com.vvz.brewbird.ui.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.vvz.brewbird.R
import com.vvz.brewbird.ui.screens.BreweryDetails
import com.vvz.brewbird.ui.screens.BreweryList
import com.vvz.brewbird.ui.screens.FavouritesList
import java.net.URLDecoder
import java.net.URLEncoder

fun NavGraphBuilder.addListScreen(navController: NavController) {
    pushComposable(route = ScreenRoutes.List.route) {
        LocalSystemBarStateProvider.current.apply {
            showBottomBar = true
            title = stringResource(id = R.string.screen_list_title)
        }
        BreweryList(onShowDetails = { navController.navigate(route = ScreenRoutes.Details.routeForItem(id = it.id, name = it.name)) })
    }
}

fun NavGraphBuilder.addDetailsScreen() {
    pushComposable(route = ScreenRoutes.Details.route) { backStackEntry ->
        val name = checkNotNull(backStackEntry.arguments?.getString("name"))
        LocalSystemBarStateProvider.current.apply {
            showBottomBar = false
            title = URLDecoder.decode(name, "UTF-8")
        }
        BreweryDetails()
    }
}

fun NavGraphBuilder.addFavouritesScreen(navController: NavController) {
    pushComposable(route = ScreenRoutes.Favourites.route) {
        LocalSystemBarStateProvider.current.apply {
            showBottomBar = true
            title = stringResource(id = R.string.screen_favourites_title)
        }
        FavouritesList(onShowDetails = { navController.navigate(route = ScreenRoutes.Details.routeForItem(id = it.id, name = it.name)) })
    }
}


sealed class ScreenRoutes(val route: String) {

    object List : ScreenRoutes(route = "screen_list")

    object Favourites : ScreenRoutes(route = "screen_favourites")

    object Details : ScreenRoutes(route = "screen_details/{id}/{name}") {
        fun routeForItem(id: String, name: String) = route.replace("{id}", id).replace("{name}", URLEncoder.encode(name, "UTF-8"))
    }
}
