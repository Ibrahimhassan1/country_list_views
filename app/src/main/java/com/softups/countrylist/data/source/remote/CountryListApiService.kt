package com.softups.countrylist.data.source.remote

import retrofit2.http.GET

interface CountryListApiService {
    @GET("countries.json")
    suspend fun getCountryList(): List<CountryDto>
}
