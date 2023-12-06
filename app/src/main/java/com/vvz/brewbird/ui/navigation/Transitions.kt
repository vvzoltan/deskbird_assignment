package com.vvz.brewbird.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.pushComposable(route: String,
                                   content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)) {

    composable(route = route,
               content = content,
               enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeIn() },
               exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeOut() },
               popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeIn() },
               popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeOut() })

}