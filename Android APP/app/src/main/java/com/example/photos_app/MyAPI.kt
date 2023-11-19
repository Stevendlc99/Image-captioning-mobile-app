package com.example.photos_app


import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

interface MyAPI {

    @Multipart
    @POST("captionmodel")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("desc") desc: RequestBody
    ): Call<UploadResponse>

    companion object {
        operator fun invoke(): MyAPI {
            return Retrofit.Builder()
                .baseUrl("http://172.23.213.39:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
                .create(MyAPI::class.java)

        }
        private fun getClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .callTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS)
                .connectTimeout(120,TimeUnit.SECONDS)
                .build()
        }
    }





}