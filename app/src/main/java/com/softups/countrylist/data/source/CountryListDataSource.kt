package com.softups.countrylist.data.source

import com.softups.countrylist.data.source.remote.CountryDto

interface CountryListDataSource {
    suspend fun getCountryList(): List<CountryDto>
}