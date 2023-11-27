package ui.episode_screen

import data.SimpleCharacter

data class EpisodeState(
    val name: String,
    val airDate: String,
    val code: String,
    val characters: List<SimpleCharacter>,
)