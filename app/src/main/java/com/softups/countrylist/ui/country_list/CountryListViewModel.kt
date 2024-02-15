package com.softups.countrylist.ui.country_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.softups.countrylist.CountryListApplication
import com.softups.countrylist.common.Resource
import com.softups.countrylist.domain.GetCountryListUseCase
import com.softups.countrylist.domain.model.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CountryListState(
    val isLoading: Boolean,
    val error: String = "",
    val data: List<Country> = emptyList()
)

class CountryViewModel(
    private val getCountryListUseCase: GetCountryListUseCase
) : ViewModel() {
    private val _countryListState = MutableStateFlow(CountryListState(isLoading = true))
    val countryListState: StateFlow<CountryListState> = _countryListState

    init {
        refreshCountryList()
    }

    fun refreshCountryList() {
        viewModelScope.launch {
            getCountryListUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _countryListState.value = CountryListState(
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        _countryListState.value = CountryListState(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Success -> {
                        _countryListState.value = CountryListState(
                            isLoading = false,
                            data = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return CountryViewModel(
                    (application as CountryListApplication).appContainer.getCountryListUseCase
                ) as T
            }
        }
    }
}