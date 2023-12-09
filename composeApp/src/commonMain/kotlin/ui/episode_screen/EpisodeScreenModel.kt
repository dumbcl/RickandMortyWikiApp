package ui.episode_screen

import cafe.adriel.voyager.core.model.StateScreenModel
import data.Character
import data.Episode
import data.SimpleLocation
import ui.character_screen.CharacterScreenState

class EpisodeScreenModel() : StateScreenModel<EpisodeScreenState>(EpisodeScreenState.Init) {
    suspend fun fetchData() {
        mutableState.value = EpisodeScreenState.EpisodeContent(
            episode = Episode(
                id = 7930,
                name = "Isiah Barnes",
                airDate = "vel",
                code = "doming",
                characters = listOf(),
                url = "null"
            )
        )
    }
}