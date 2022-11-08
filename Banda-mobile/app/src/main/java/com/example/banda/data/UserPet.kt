package com.example.banda.data

import com.squareup.moshi.Json

data class UserPet(

    @field:Json(name = "type")
    val type: String,

    @field:Json(name = "user")
    val user: List<Petinformation>
)

