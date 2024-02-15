package com.softups.countrylist

import android.app.Application
import com.softups.countrylist.di.AppContainer

class CountryListApplication : Application() {

    val appContainer = AppContainer()
}