package com.example.banda
import com.example.banda.data.*
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
    ):Call<RegisterGet>

    @POST("auth/emailcheck")
    fun EmailCheck(
        @Body register: Register
    ):Call<RegisterGet>

    @POST("auth/groupcheck")
    fun GroupCheck(
        @Body groupcheck: Groupcheck
    ):Call<RegisterGet>

    @POST("auth/petregister")
    fun PetRegister(
        @Body petRegister: PetRegister
    ):Call<RegisterGet>
}