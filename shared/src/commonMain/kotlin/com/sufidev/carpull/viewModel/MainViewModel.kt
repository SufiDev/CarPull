package com.sufidev.carpull.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sufidev.carpull.CarPullSDK
import com.sufidev.carpull.entity.TripEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sdk: CarPullSDK
) : ViewModel() {

    private val _state = MutableStateFlow(TripsScreenState())
    val state: StateFlow<TripsScreenState> = _state.asStateFlow()

    init {
        loadTrips()
    }

    fun loadTrips(forceReload: Boolean = false) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val trips = sdk.getTrips(forceReload = forceReload)
                _state.update {
                    it.copy(
                        isLoading = false,
                        trips = trips,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }
}

data class TripsScreenState(
    val isLoading: Boolean = false,
    val trips: List<TripEntity> = emptyList(),
    val error: String? = null
)