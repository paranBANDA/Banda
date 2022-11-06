package com.example.banda.data

import com.squareup.moshi.Json

data class InfoPetFeel (
    @field:Json(name = "date")
    val date: List<String>,

    @field:Json(name = "feel")
    val feel: List<String>
)