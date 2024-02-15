package com.softups.countrylist.data.source.repository

import com.softups.countrylist.data.source.CountryListDataSource
import com.softups.countrylist.data.source.remote.CountryDto
import com.softups.countrylist.domain.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultCountryRepository(private val countryListDataSource: CountryListDataSource) :
    CountryRepository {
    override suspend fun getCountryList(): List<CountryDto> {
        return withContext(Dispatchers.IO) {
            countryListDataSource.getCountryList()
        }
    }
}