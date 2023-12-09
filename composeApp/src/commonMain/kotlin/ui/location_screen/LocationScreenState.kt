package ui.location_screen

import data.Location
import data.SimpleCharacter

sealed class LocationScreenState {
    data object Init : LocationScreenState()
    data object Loading : LocationScreenState()
    data object Error : LocationScreenState()
    data class LocationContent(val location: Location) : LocationScreenState()

}