package com.example.bibliomate_android.api

import com.example.bibliomate_android.models.BookResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("volumes")
    fun searchBooks(@Query("q") query: String): Call<BookResponse>

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/books/v1/"

        fun create(): GoogleBooksApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(GoogleBooksApiService::class.java)
        }
    }
}
