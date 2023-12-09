package ui.location_screen

import cafe.adriel.voyager.core.model.StateScreenModel
import data.Episode
import data.Location
import data.RickAndMortyRepository
import ui.episode_screen.EpisodeScreenState

class LocationScreenModel(val repository: RickAndMortyRepository) : StateScreenModel<LocationScreenState>(LocationScreenState.Init) {
    var id = 0

    suspend fun fetchData() {
        val location = repository.getLocation(id)
        if (location == null) {
            mutableState.value = LocationScreenState.Error
        } else {
            mutableState.value = LocationScreenState.LocationContent(
                location = location
            )
        }
    }
}