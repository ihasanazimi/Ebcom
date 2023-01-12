package com.example.ebcom.services.http

import com.example.ebcom.BuildConfig
import com.example.ebcom.model.RestaurantsObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {


    @GET("096e53fd-31db-4150-a886-302ee005cbd0")
    suspend fun getRestaurant() : RestaurantsObject


}


private const val BASE_URL = "https://run.mocky.io/v3/"


fun logger(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) interceptor.level = HttpLoggingInterceptor.Level.BODY
    else interceptor.level = HttpLoggingInterceptor.Level.NONE
    return interceptor
}

private val client = OkHttpClient.Builder()
    .addInterceptor(logger())
    .addInterceptor(Interceptor {
        val oldRequest = it.request()
        val newRequestBuilder = oldRequest.newBuilder()
        newRequestBuilder.addHeader("x-token" , "ebcom12345678910qwertyuioplkjhgfdsazxcvbnm")
        return@Interceptor it.proceed(newRequestBuilder.build())
    }).build()


val apiService : ApiService by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    retrofit.create(ApiService::class.java)
}