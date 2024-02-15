package com.softups.countrylist.data.source.remote

import com.softups.countrylist.data.source.CountryListDataSource

class CountryRemoteDataSource(private val countryListApiService: CountryListApiService) :
    CountryListDataSource {
    override suspend fun getCountryList(): List<CountryDto> {
        return countryListApiService.getCountryList()
    }
}
