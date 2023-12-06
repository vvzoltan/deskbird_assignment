package com.vvz.brewbird.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.vvz.brewbird.data.BreweryRepository
import com.vvz.brewbird.domain.models.BreweryPreview
import com.vvz.brewbird.domain.models.BreweryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.max


@HiltViewModel
class BreweryListViewModel @Inject constructor(private val repository: BreweryRepository) : ViewModel() {

    private var pagingSource: BrewerySource? = null
    val breweries: Flow<PagingData<BreweryPreview>> = Pager(PagingConfig(pageSize = 10,
                                                                         prefetchDistance = 20,
                                                                         enablePlaceholders = true,
                                                                         maxSize = 200,
                                                                         jumpThreshold = 100,
                                                                         initialLoadSize = 50)) {
        BrewerySource(repository = repository, filter = filter).apply { pagingSource = this }
    }.flow.cachedIn(viewModelScope)

    var filter by mutableStateOf<BreweryType?>(null)
        private set

    val favourites: Flow<Set<String>> = repository.getFavouriteIds()

    fun updateFavorite(id: String, isFavourite: Boolean) {
        viewModelScope.launch {
            when (isFavourite) {
                true  -> repository.addFavourite(id)
                false -> repository.removeFavourite(id)
            }
        }
    }

    fun updateFilter(value: BreweryType?) {
        filter = value
        pagingSource?.invalidate()
    }

    fun reload() {
        filter = null
        pagingSource?.invalidate()
    }

    enum class State { Loading, Results, Empty, Error }

}


class BrewerySource constructor(private val repository: BreweryRepository,
                                private val filter: BreweryType?) : PagingSource<Int, BreweryPreview>() {

    override val jumpingSupported: Boolean = true
    private val startPage = 1

    override fun getRefreshKey(state: PagingState<Int, BreweryPreview>): Int? {
        return state.anchorPosition?.let { max(startPage, it / state.config.pageSize) }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreweryPreview> {

        return try {
            val nextPage = params.key ?: startPage
            val data = repository.getBreweries(page = nextPage, type = filter)
            val pageIndex = data.second.page
            val newCount = data.first.size
            val total = data.second.total
            val itemsBefore = max(pageIndex - 1, 0) * params.loadSize
            val itemsAfter = total - (itemsBefore + newCount)
            val prevKey = if (pageIndex > startPage) pageIndex - 1 else null
            val nextKey = if (pageIndex < (ceil(data.second.total.toDouble() / data.second.resultsPerPage))) pageIndex + 1 else null
            LoadResult.Page(data = data.first,
                            prevKey = prevKey,
                            nextKey = nextKey,
                            itemsBefore = itemsBefore,
                            itemsAfter = itemsAfter)
        } catch (exception: Throwable) {
            LoadResult.Error(exception)
        }
    }
}