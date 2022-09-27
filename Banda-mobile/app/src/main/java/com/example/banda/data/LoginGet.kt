package com.example.banda.data

data class LoginGet(
    val type : String,
    val data : String,
    val code: Int,
    val message: String,
    val token: String
)