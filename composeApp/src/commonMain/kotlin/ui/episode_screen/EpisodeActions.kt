package ui.episode_screen

sealed class EpisodeActions{
    object NavigateUp: EpisodeActions()
    object NavigateToMainScreen: EpisodeActions()
    object NavigateToCharacter: EpisodeActions()
}