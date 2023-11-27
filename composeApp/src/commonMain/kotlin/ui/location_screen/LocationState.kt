package ui.location_screen

import data.SimpleCharacter

data class LocationState(
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<SimpleCharacter>,
)