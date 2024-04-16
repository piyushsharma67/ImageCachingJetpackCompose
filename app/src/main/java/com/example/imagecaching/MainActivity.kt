package com.example.imagecaching

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.collection.LruCache
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.imagecaching.ui.theme.ImageCachingTheme
import com.example.imagecaching.ui.theme.Purple100
import com.example.imagecaching.ui.theme.Purple40
import com.example.imagecaching.ui.theme.Purple80
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //a memory cache has been initialised
    private val cache = LruCache<String, Bitmap>(10 * 1024 * 1024)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageCachingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //  **** viewModel instance
                    val viewModel: MyViewModel = viewModel()

                    ImageList(modifier = Modifier,viewModel,cache)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageList(modifier: Modifier, viewModel: MyViewModel, cache: LruCache<String, Bitmap>){

    //** collecting the flow
    val lazyPagingItems: LazyPagingItems<String> = viewModel.getImages().collectAsLazyPagingItems()
    val isRefreshing = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Image Caching", color = Color.White) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple100)
        ) }
    ) {
       Box(
           modifier = Modifier
               .fillMaxSize()
               .padding(it)
       ) {
           LazyVerticalGrid(
               columns = GridCells.Fixed(3), // Fixed number of columns (3 columns)
               modifier = Modifier.fillMaxSize() // Fill the available vertical space
           ) {
               items(lazyPagingItems.itemCount) { index ->
                   val imageUrl = lazyPagingItems[index] ?: return@items
                   ImageComponent(modifier,imageUrl,cache)
               }
           }
       }
    }
}

