package com.example.apigameofthrones

import retrofit2.http.GET
import retrofit2.http.Query

interface GameOfThronesApiService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Character>
}