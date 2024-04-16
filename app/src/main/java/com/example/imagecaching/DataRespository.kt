package com.example.imagecaching

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor() {

    //function which would mimic a API call and return a list of url addresses
    suspend fun getImages(): List<String> {
        delay(2000)

        //deliberately provided a incorrect url at 0 index to mimic error message.

        val images=mutableListOf(
                "https://molo17.com/wp-content/uploads/2021/11/StudioCompos", //
        "https://static.platzi.com/media/user_upload/Jetpack_logo-2-04c3fdd5-545c-4881-a2cd-2f5d1d9b4299.jpg",
        "https://naps.com.mx/blog/wp-content/uploads/2022/10/curso-gratuito-android-jetpack.jpg",
        "https://molo17.com/wp-content/uploads/2021/11/StudioCompose1",
        "https://static.platzi.com/media/user_upload/Jetpack_logo-2-04c3fdd5-545c-4881-a2cd-2f5d1d9b4299.jpg",
        "https://naps.com.mx/blog/wp-content/uploads/2022/10/curso-gratuito-android-jetpack.jpg",
        "https://molo17.com/wp-content/uploads/2021/11/StudioCompose10.jpg",
        "https://static.platzi.com/media/user_upload/Jetpack_logo-2-04c3fdd5-545c-4881-a2cd-2f5d1d9b4299.jpg",
        "https://naps.com.mx/blog/wp-content/uploads/2022/10/curso-gratuito-android-jetpack.jpg",
        "https://molo17.com/wp-content/uploads/2021/11/StudioCompose10.jpg",
        "https://static.platzi.com/media/user_upload/Jetpack_logo-2-04c3fdd5-545c-4881-a2cd-2f5d1d9b4299.jpg",
        "https://naps.com.mx/blog/wp-content/uploads/2022/10/curso-gratuito-android-jetpack.jpg"
        )

        images.shuffle()

        return images
    }
}