package com.example.sipas.api

import com.example.sipas.model.Orangtua
import com.example.sipas.model.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OrangtuaApi {

    @POST("/orangtua")
    suspend fun insertOrangtua(@Body orangtua: Orangtua): Response<Orangtua>
}