package com.example.sipas.model

data class Response<out T>(
    val message: String,
    val data: T,
    val success: Boolean,
    val jwtToken: String
)
