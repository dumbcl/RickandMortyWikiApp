package ui.location_screen

sealed class LocationActions{
    object NavigateUp: LocationActions()
    object NavigateToMainScreen: LocationActions()
    object NavigateToCharacter: LocationActions()
}