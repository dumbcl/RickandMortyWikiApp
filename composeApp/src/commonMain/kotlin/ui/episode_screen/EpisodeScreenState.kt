package ui.episode_screen

import data.Episode
import data.SimpleCharacter

sealed class EpisodeScreenState{
    data object Init: EpisodeScreenState()
    data object Loading: EpisodeScreenState()
    data object Error: EpisodeScreenState()
    data class EpisodeContent(val episode: Episode) : EpisodeScreenState()
}