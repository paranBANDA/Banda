package com.example.banda
import com.example.banda.data.Login
import com.example.banda.data.LoginGet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("login")
    fun LocalLogin(
        @Body login: Login
    ):Call<LoginGet>
}