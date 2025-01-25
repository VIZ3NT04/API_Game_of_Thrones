package com.example.apigameofthrones

data class Character(
    val url: String,
    val name: String,
    val culture: String,
    val died: String,
    val titles: List<String>
)