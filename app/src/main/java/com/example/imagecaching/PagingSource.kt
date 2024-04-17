//package com.example.imagecaching
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//
//
//class ImagePagingSource constructor(private val repository: DataRepository) :
//    PagingSource<Int, String>()  {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
//        return try {
//            // Load your data from repository
//            val images = repository.getImages()
//            // Return LoadResult.Page with data and next key
//            LoadResult.Page(
//                data = images,
//                prevKey = null, // Paging 3 does not support previous paging
//                nextKey = params.key?.plus(1) ?: 1 // Increment page key for next page
//            )
//        } catch (e: Exception) {
//            // Handle error
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
//        return state.anchorPosition
//    }
//}