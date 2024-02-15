package com.softups.countrylist.domain.repository

import com.softups.countrylist.data.source.remote.CountryDto

interface CountryRepository {
    suspend fun getCountryList(): List<CountryDto>
}