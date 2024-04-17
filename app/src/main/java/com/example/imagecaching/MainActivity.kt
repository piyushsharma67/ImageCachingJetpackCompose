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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagecaching.apiDataClass.ApiDataClass
import com.example.imagecaching.ui.theme.ImageCachingTheme
import com.example.imagecaching.ui.theme.Purple100
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
    val items by viewModel.data.collectAsState()
    var data by remember { mutableStateOf<ApiDataClass?>(null) }
    var errorMessage by remember { mutableStateOf<String?>("") }
    var loading by remember { mutableStateOf(false) }

    when (items) {
        is ApiResponse.Loading -> {
            loading=true
        }
        is ApiResponse.Success -> {
            // Data is available, show the content and turn off the loading
            loading=false
            data = (items as ApiResponse.Success<ApiDataClass?>).data
        }
        is ApiResponse.Error -> {
            // Error occurred, show an error message and turn off the error
            loading=false
            errorMessage = (items as ApiResponse.Error<ApiDataClass?>).errorMessage
        }

        else -> {}
    }

    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Image Caching", color = Color.White) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple100)
        ) }
    ) {
       Box(
           modifier = Modifier
               .fillMaxSize()
               .padding(it),
           contentAlignment = Alignment.Center
       ) {
           if(loading){
               CircularProgressIndicator()
           }else if(errorMessage!=""){
               errorMessage?.let { it1 -> Text(it1) }
           }else{
               LazyVerticalGrid(
                   columns = GridCells.Fixed(3), // Fixed number of columns (3 columns)
                   modifier = Modifier.fillMaxSize() // Fill the available vertical space
               ) {
                   data?.size?.let { it1 ->
                       items(it1) { index ->
                           val domain= data!![index].thumbnail?.domain
                           val basePath=data!![index].thumbnail?.basePath
                           val key=data!![index].thumbnail?.key
                           val imageUrl = "$domain/$basePath/0/$key"

                           ImageComponent(modifier,imageUrl,cache)
                       }
                   }
               }
           }
       }
    }
}

