package ui.character_screen

import cafe.adriel.voyager.core.model.StateScreenModel
import data.Character
import data.RickAndMortyRepository
import data.SimpleLocation
import data.util.httpsStringToId
import ui.episode_screen.EpisodeScreenState

class CharacterScreenModel(val repository: RickAndMortyRepository) : StateScreenModel<CharacterScreenState>(CharacterScreenState.Init) {
    var id = 0

    suspend fun fetchData() {
        val character = repository.getCharacter(id)
        if (character == null) {
            mutableState.value = CharacterScreenState.Error
        } else {
            mutableState.value = CharacterScreenState.CharacterContent(
                character = character.copy(episodes = character.episodes.map { repository.getEpisode(it.httpsStringToId())?.name.toString() + "_" + it.httpsStringToId().toString()})
            )
        }
    }
}