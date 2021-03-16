package com.specialtopics.loginwithflask.retrofit

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.specialtopics.loginwithflask.BASE_URL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class UserApiService {
    interface UserApiService {
        //@GET("/usergroup")
        @FormUrlEncoded
        @POST("/api/login")
        fun loginUser(
            @Field("username") username: String,
            @Field("pass") password: String
        ): Call<ResponseBody>

        @FormUrlEncoded
        @POST("/api/register")
        fun registerUser(
            @Field("username") username: String,
            @Field("pass") password: String
        ): Call<ResponseBody>

    }

    companion object {
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .build()
        var service = retrofit.create(UserApiService::class.java)
    }


}