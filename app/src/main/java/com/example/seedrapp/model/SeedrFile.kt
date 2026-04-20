package com.example.seedrapp.model
data class SeedrFile(
    val id: String,
    val name: String,
    val type: String,
    val size: Long,
    val streamUrl: String?,
    val downloadUrl: String?
)
