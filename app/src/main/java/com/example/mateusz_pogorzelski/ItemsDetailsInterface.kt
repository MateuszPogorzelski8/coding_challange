package com.example.mateusz_pogorzelski

import java.net.URL

// NOTE USED
class ItemsDetailsInterface (
    val largeImageURL: URL,
    val user: String,
    val tagList: List<String>,
    val likes: Int,
    val download: Int,
    val comments: Int
)