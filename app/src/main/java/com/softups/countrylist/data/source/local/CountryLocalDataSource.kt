package com.softups.countrylist.data.source.local

import com.softups.countrylist.data.source.CountryListDataSource
import com.softups.countrylist.data.source.remote.CountryDto

// Not required, but might be useful in the future for persistence
class CountryLocalDataSource : CountryListDataSource {
    override suspend fun getCountryList(): List<CountryDto> {
        return listOf(
            CountryDto(
                name = "Afghanistan",
                code = "AF",
                capital = "Kabul",
                region = "AS",
                flag = "https://restcountries.eu/data/afg.svg"
            ),
            CountryDto(
                name = "Åland Islands",
                code = "AX",
                capital = "Mariehamn",
                region = "EU",
                flag = "https://restcountries.eu/data/ala.svg"
            ),
        )
    }
}
