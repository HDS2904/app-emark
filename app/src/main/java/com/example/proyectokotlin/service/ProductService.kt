package com.example.proyectokotlin.service

import com.example.proyectokotlin.model.RespNode
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET ("/producto")
    fun getProducts(): Call<RespNode>
}