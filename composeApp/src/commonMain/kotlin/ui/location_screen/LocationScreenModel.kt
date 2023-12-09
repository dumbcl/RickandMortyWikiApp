package ui.location_screen

import cafe.adriel.voyager.core.model.StateScreenModel
import data.Episode
import data.Location
import ui.episode_screen.EpisodeScreenState

class LocationScreenModel() : StateScreenModel<LocationScreenState>(LocationScreenState.Init) {
    suspend fun fetchData() {
        mutableState.value = LocationScreenState.LocationContent(
            location = Location(
                id = 9458,
                name = "Zachary Fernandez",
                type = "quidam",
                dimension = "null",
                residents = listOf(),
                url = "null"
            )
        )
    }
}