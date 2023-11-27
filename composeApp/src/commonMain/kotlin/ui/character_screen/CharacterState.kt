package ui.character_screen

import data.SimpleEpisode
import data.SimpleLocation

data class CharacterState(
    val image: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String = "",
    val origin: SimpleLocation,
    val location: SimpleLocation,
    val episodes: List<SimpleEpisode>,
)