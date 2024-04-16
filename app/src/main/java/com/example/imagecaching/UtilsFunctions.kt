package com.example.imagecaching

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

//** function to download the image *****
suspend fun downloadImage(urlString: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        var inputStream: InputStream? = null
        var bitmap: Bitmap? = null
        try {
            val url = URL(urlString)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            inputStream = connection.inputStream
            bitmap = inputStream?.let { BitmapFactory.decodeStream(it) }
        } catch (e: IOException) {
            throw e //throwing error in case there is an error
        } finally {
            inputStream?.close() //closing the stream connection
        }
        bitmap
    }
}
//*******  function to load image from the disk cache *******

fun loadBitmapFromDiskCache(context: Context, url: String): Bitmap? {
    val directory = File(context.cacheDir, "image_cache")
    if (!directory.exists()) {
        directory.mkdirs() //if directory does not exists then create it
    }

    val file = File(directory, url.hashCode().toString())
    if (file.exists()) {
        try {
            val fis = FileInputStream(file)
            return BitmapFactory.decodeStream(fis)
        } catch (e: IOException) {
            throw e //throwing error
        }
    }
    return null
}

//****** function to save image in the disk cache *****
fun saveBitmapToDiskCache(context: Context, url: String, bitmap: Bitmap) {
    val directory = File(context.cacheDir, "image_cache")
    if (!directory.exists()) {
        directory.mkdirs()
    }

    val file = File(directory, url.hashCode().toString())
    try {
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        throw e
    }
}