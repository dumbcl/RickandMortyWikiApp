package ui.character_screen

import data.Character
import data.Location
import data.SimpleLocation

sealed class CharacterScreenState {
    data object Init: CharacterScreenState()
    data object Loading : CharacterScreenState()
    data object Error : CharacterScreenState()
    data class CharacterContent(
        val character: Character,
    ) : CharacterScreenState()

}