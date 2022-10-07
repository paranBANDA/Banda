package com.example.banda
import com.example.banda.data.Login
import com.example.banda.data.LoginGet
import com.example.banda.data.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface RetrofitService {
    @POST("login")
    fun LocalLogin(
        @Body login: Login
    ):Call<LoginGet>

    @POST("register")
    fun LocalRegister(
        @Body register: Register
    ):Call<Register>
}