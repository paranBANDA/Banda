package com.example.banda.data

data class user(
    val _id: Id,
    val email: String,
    val familyId: FamilyId,
    val kakaoidentifier: String,
    val lastwalking: String,
    val locationallow: Boolean,
    val name: String,
    val nickname: String,
    val pw: String,
    val type: String,
    val walkingstate: Boolean
)