package ui.character_screen

sealed class CharacterActions{
    object NavigateUp: CharacterActions()
    object NavigateToMainScreen: CharacterActions()
    object NavigateToLocation: CharacterActions()
    object NavigateToEpisode: CharacterActions()
}