package ui.episode_screen

import cafe.adriel.voyager.core.model.StateScreenModel
import data.Character
import data.Episode
import data.RickAndMortyRepository
import data.SimpleLocation
import data.util.httpsStringToId
import ui.character_screen.CharacterScreenState
import ui.location_screen.LocationScreenState

class EpisodeScreenModel(val repository: RickAndMortyRepository) : StateScreenModel<EpisodeScreenState>(EpisodeScreenState.Init) {
    var id = 0

    suspend fun fetchData() {
        val episode = repository.getEpisode(id)
        if (episode == null) {
            mutableState.value = EpisodeScreenState.Error
        } else {
            mutableState.value = EpisodeScreenState.EpisodeContent(
                episode = episode.copy(characters = episode.characters.map { repository.getCharacter(it.httpsStringToId())?.name.toString() + "_" + it.httpsStringToId().toString()})
            )
        }
    }
}