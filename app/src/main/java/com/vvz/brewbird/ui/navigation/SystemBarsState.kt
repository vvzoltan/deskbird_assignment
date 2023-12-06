package com.vvz.brewbird.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class SystemBarsState internal constructor() {

    var title by mutableStateOf("")
    var showBackButton by mutableStateOf(false)
    var showBottomBar by mutableStateOf(true)
    var onBack: (() -> Unit)? = null
    var actionIcon by mutableStateOf<(@Composable () -> Unit)?>(null)

}


@Composable
fun rememberSystemBarState() = remember {
    SystemBarsState()
}


val LocalSystemBarStateProvider = compositionLocalOf<SystemBarsState> { error("State not provided") }