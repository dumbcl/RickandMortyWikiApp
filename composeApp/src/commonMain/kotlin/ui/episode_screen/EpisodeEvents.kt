package ui.episode_screen

sealed class EpisodeEvents{
    object NavigateUp: EpisodeEvents()
    object NavigateToMainScreen: EpisodeEvents()
    object NavigateToCharacter: EpisodeEvents()
}