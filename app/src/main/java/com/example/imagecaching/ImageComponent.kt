package com.example.imagecaching

import android.graphics.Bitmap
import android.util.Log
import androidx.collection.LruCache
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.IOException


@Composable
fun ImageComponent(modifier: Modifier = Modifier, imageUrl:String, cache: LruCache<String, Bitmap>){

    // **** all the image loading and caching logic has been kept inside this component to make each component have their own
    // loading state and error state

    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember{ mutableStateOf("") }

    //** application context which is required to access android resources like FileSystem,roomDB,Retrofit.
    val context = LocalContext.current.applicationContext

    LaunchedEffect(key1 = Unit){
        isLoading = true //before checking setting the loading to true
        imageBitmap = cache.get(imageUrl) //firstly checking if the image exists in the memory cache

        if (imageBitmap == null) {
            imageBitmap = loadBitmapFromDiskCache(context, imageUrl) // Trying to load image from disk cache if it does not exists in memory cache
        }

        if (imageBitmap == null) {
            try {
                val downloadedBitmap = downloadImage(imageUrl) //downloading the image if not found in cache
                if (downloadedBitmap != null) {
                    cache.put(imageUrl, downloadedBitmap) // once downloaded saved the image inside the memory cache
                    saveBitmapToDiskCache(context, imageUrl, downloadedBitmap)  // saved the image inside the disk cache
                    imageBitmap = downloadedBitmap
                }
            } catch (e: IOException) {
                isLoading=false
                error=e.message.toString() // setting the caught error
            }finally {
                isLoading = false
            }
        }else{
            isLoading = false
        }
    }

    if (isLoading) {
        // Show loader while the image is loading
        Box(
            modifier = modifier
                .padding(4.dp)
                .size(200.dp)
                .border(1.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // You can customize this with your preferred loader icon i have shown a loading indicator
        }
    } else if(error!=""){
        Box(
            modifier = modifier
                .padding(4.dp)
                .size(200.dp)
                .border(1.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(text = error,style = TextStyle(color = Color.Red, fontSize = 16.sp))
        }
    }

    else {
        // Show image once it's loaded
        imageBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = modifier
                    .padding(4.dp)
                    .size(200.dp)
                    .border(1.dp, Color.Black), // Adjust the size of each image
                contentScale = ContentScale.Crop // Scale the images to fill the space
            )
        }
    }
}