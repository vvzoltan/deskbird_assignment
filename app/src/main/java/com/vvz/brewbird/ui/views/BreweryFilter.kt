package com.vvz.brewbird.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vvz.brewbird.domain.models.BreweryType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreweryFilter(availableTypes: List<BreweryType>,
                  selected: BreweryType?,
                  onUpdateSelection: (BreweryType?) -> Unit) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(expanded = isExpanded,
                           onExpandedChange = { isExpanded = false },
                           modifier = Modifier
                               .fillMaxWidth()
                               .height(60.dp)) {

        MenuAnchorRow(title = "%s: %s".format("Filter", selected?.name ?: "All"),
                      icon = if (isExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                      onSelect = { isExpanded = true })

        ExposedDropdownMenu(expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }) {

            (listOf<BreweryType?>(null) + availableTypes).forEach { type ->

                FilterRow(title = type?.name ?: "All", onSelect = {
                    isExpanded = false
                    onUpdateSelection(type)
                })
            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdownMenuBoxScope.MenuAnchorRow(title: String, icon: ImageVector, onSelect: () -> Unit) {

    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(onClick = onSelect)) {

        Text(text = title,
             style = MaterialTheme.typography.titleLarge,
             modifier = Modifier
                 .menuAnchor()
                 .padding(horizontal = 20.dp, vertical = 8.dp)
                 .align(Alignment.CenterStart))

        Icon(imageVector = icon, contentDescription = null, modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(horizontal = 20.dp, vertical = 8.dp))

        Divider(modifier = Modifier.align(Alignment.BottomCenter))

    }

}


@Composable
private fun FilterRow(title: String, onSelect: () -> Unit) {

    Box(modifier = Modifier
        .fillMaxSize()
        .height(50.dp)
        .clickable(onClick = onSelect)) {

        Text(text = title,
             style = MaterialTheme.typography.titleMedium,
             modifier = Modifier
                 .padding(horizontal = 20.dp,
                          vertical = 8.dp)
                 .align(Alignment.CenterStart))
    }

}