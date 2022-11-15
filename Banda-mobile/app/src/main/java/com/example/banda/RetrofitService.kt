package com.example.banda
import com.example.banda.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @GET("info/pet")
    fun PetInfo(
        @Query("id") petId: Int
    ):Call<InfoPetFeel>

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

    @POST("main/showuserpet")
    fun ShowUserPet(
        @Body groupcheck: Groupcheck
    ):Call<UserPet>

    @POST("polaroid/getDiarybyPet")
    fun getDiaryByPet(
        @Body findDiary: FindDiary
    ):Call<DiaryGet>


}