package com.vvz.brewbird.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vvz.brewbird.R

@Composable
fun ContentError(error: Throwable? = null,
                 onRetry: (() -> Unit)? = null) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center,
           modifier = Modifier.fillMaxSize()) {

        Text(text = stringResource(id = R.string.content_error_message))
        Spacer(modifier = Modifier.height(20.dp))

        onRetry?.let {
            Button(onClick = it) {
                Text(text = stringResource(id = R.string.content_error_try_again))
            }
        }

    }
}