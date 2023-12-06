package com.vvz.brewbird.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvz.brewbird.data.BreweryRepository
import com.vvz.brewbird.domain.models.BreweryPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesListViewModel @Inject constructor(private val repository: BreweryRepository) : ViewModel() {

    var favourites: Set<String> = setOf()
        private set

    var state by mutableStateOf<State>(State.Loading)

    init {
        loadList()
    }


    fun loadList() {
        viewModelScope.launch {
            state = State.Loading
            try {
                repository
                    .getFavouriteIds()
                    .collectLatest {
                        favourites = it
                        state = when (favourites.isEmpty()) {
                            true  -> State.Empty
                            false -> State.Result(items = repository.loadFavourites(ids = favourites))
                        }
                    }
            } catch (e: Throwable) {
                state = State.Error(error = e)
            }
        }
    }


    fun removeFavourite(id: String) {
        viewModelScope.launch {
            repository.removeFavourite(id)
        }
    }


    sealed class State {
        object Loading : State()
        object Empty : State()
        class Result(val items: List<BreweryPreview>) : State()
        class Error(val error: Throwable) : State()
    }

}