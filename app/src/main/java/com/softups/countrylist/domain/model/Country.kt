package com.softups.countrylist.domain.model

data class Country(
    val name: String,
    val region: String,
    val code: String?,
    val capital: String,
)
