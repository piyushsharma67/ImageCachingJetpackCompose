package com.example.imagecaching
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: DataRepository):ViewModel(){

    private var currentPage = 1

    fun getImages(): Flow<PagingData<String>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false // Depending on your use case, you may want to change this
            ),
            pagingSourceFactory = { ImagePagingSource(repository) }
        ).flow.map { it ->
            it
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}