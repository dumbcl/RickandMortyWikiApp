package ui.location_screen

sealed class LocationEvents{
    object NavigateUp: LocationEvents()
    object NavigateToMainScreen: LocationEvents()
    object NavigateToCharacter: LocationEvents()
}