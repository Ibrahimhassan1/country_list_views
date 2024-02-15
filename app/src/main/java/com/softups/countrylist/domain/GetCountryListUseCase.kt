package com.softups.countrylist.domain

import com.softups.countrylist.common.Constants.NOT_CONNECTED_ERROR
import com.softups.countrylist.common.Constants.UN_EXPECTED_ERROR
import com.softups.countrylist.common.Resource
import com.softups.countrylist.data.source.remote.toCountry
import com.softups.countrylist.domain.model.Country
import com.softups.countrylist.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetCountryListUseCase(private val countryRepository: CountryRepository) {

    operator fun invoke(): Flow<Resource<List<Country>>> = flow {
        try {
            emit(Resource.Loading())
            val countryList = countryRepository.getCountryList().map { it.toCountry() }
            emit(Resource.Success(countryList))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: UN_EXPECTED_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(NOT_CONNECTED_ERROR))
        }
    }
}