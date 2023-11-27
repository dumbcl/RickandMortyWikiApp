package ui.character_screen

sealed class CharacterEvents {
    object NavigateUp: CharacterEvents()
    object NavigateToMainScreen: CharacterEvents()
    object NavigateToLocation: CharacterEvents()
    object NavigateToEpisode: CharacterEvents()
}