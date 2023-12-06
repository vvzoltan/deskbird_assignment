package com.vvz.brewbird.ui.screens

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvz.brewbird.data.BreweryRepository
import com.vvz.brewbird.domain.models.BreweryDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BreweryDetailsViewModel @Inject constructor(private val repository: BreweryRepository,
                                                  savedStateHandle: SavedStateHandle) : ViewModel() {

    var id: String = checkNotNull(savedStateHandle["id"])

    var state by mutableStateOf<State>(State.Loading)
        private set

    val isFavourite by derivedStateOf {
        repository
            .getFavouriteIds()
            .map { it.contains(id) }
    }


    init {
        viewModelScope.launch {
            state = try {
                val brewery = repository.getBreweryDetails(id = id)
                State.Loaded(details = brewery)
            } catch (e: Throwable) {
                State.Error(error = e)
            }
        }
    }

    fun updateFavourite(value: Boolean) {
        viewModelScope.launch {
            when (value) {
                true  -> repository.addFavourite(id)
                false -> repository.removeFavourite(id)
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val details: BreweryDetails) : State()
        data class Error(val error: Throwable) : State()
    }

}