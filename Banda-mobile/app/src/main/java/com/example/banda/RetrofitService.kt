package com.example.banda
import com.example.banda.data.Login
import com.example.banda.data.LoginGet
import com.example.banda.data.Register
import com.example.banda.data.RegisterGet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface RetrofitService {
    @POST("auth/login")
    fun LocalLogin(
        @Body login: Login
    ):Call<LoginGet>

    @POST("auth/register")
    fun LocalRegister(
        @Body register: Register
    ):Call<Register>

    @POST("auth/emailcheck")
    fun EmailCheck(
        @Body register: Register
    ):Call<RegisterGet>
}