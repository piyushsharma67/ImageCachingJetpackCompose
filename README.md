Project Name
Description
This project is a Jetpack Compose application that demonstrates image caching using both memory and disk cache. It utilizes Hilt for dependency injection and follows the MVVM architecture pattern with ViewModel.

Features
Image loading from remote URLs.
Caching of images in memory using LruCache.
Caching of images on disk.
MVVM architecture with ViewModel and Repository pattern.
Dependency injection with Hilt.
Requirements
Android Studio Arctic Fox or later
Kotlin version 1.5.0 or later
Android Gradle Plugin version 7.0.0 or later
Setup
Clone the repository to your local machine:
bash
Copy code
git clone [<repository-url>](https://github.com/piyushsharma67/ImageCachingJetpackCompose)
Open the project in Android Studio.

Build and run the project on an emulator or a physical device.

Usage
The application loads images from remote URLs and caches them in memory and disk for faster retrieval in subsequent accesses.

Libraries Used
Jetpack Compose
Hilt
Android Architecture Components (ViewModel)
Kotlin Coroutines
Picasso (for image loading)
LruCache
Material Components for Android
Contributing
Contributions are welcome! If you find any bugs or have suggestions for improvement, please open an issue or submit a pull request.

License
This project is licensed under the MIT License.
