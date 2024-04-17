package com.example.imagecaching.apiDataClass

data class ApiDataClassItem(
    val backupDetails: BackupDetails?,
    val coverageURL: String?,
    val id: String?,
    val language: String?,
    val mediaType: Int?,
    val publishedAt: String?,
    val publishedBy: String?,
    val thumbnail: Thumbnail?,
    val title: String?
)