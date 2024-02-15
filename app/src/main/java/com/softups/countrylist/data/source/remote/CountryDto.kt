package com.softups.countrylist.data.source.remote

import com.softups.countrylist.domain.model.Country

data class CountryDto(
    val name: String,
    val region: String,
    val code: String?,
    val capital: String,
    val flag: String
)

// Mapping from data layer to UI layer
fun CountryDto.toCountry(): Country {
    return Country(
        name = name,
        region = region,
        code = code,
        capital = capital
    )
}
