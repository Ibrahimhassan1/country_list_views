package com.softups.countrylist.di

import android.util.Log
import com.softups.countrylist.common.Constants.BASE_URL
import com.softups.countrylist.common.Constants.OK_HTTP_CLIENT_TIME_OUT
import com.softups.countrylist.data.source.remote.CountryListApiService
import com.softups.countrylist.data.source.remote.CountryRemoteDataSource
import com.softups.countrylist.data.source.repository.DefaultCountryRepository
import com.softups.countrylist.domain.GetCountryListUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// Container of objects shared across the whole app (Manual Injection)
class AppContainer {

    private val remoteDataSource = CountryRemoteDataSource(getRetrofitApiService())

    private val countryRepository = DefaultCountryRepository(remoteDataSource)

    val getCountryListUseCase = GetCountryListUseCase(countryRepository)

    private fun getRetrofitApiService(): CountryListApiService {
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message)
            }
        })

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(OK_HTTP_CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(OK_HTTP_CLIENT_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryListApiService::class.java)
    }
}