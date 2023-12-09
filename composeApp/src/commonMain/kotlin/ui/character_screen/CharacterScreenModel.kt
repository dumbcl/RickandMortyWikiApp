package ui.character_screen

import cafe.adriel.voyager.core.model.StateScreenModel
import data.Character
import data.SimpleLocation

class CharacterScreenModel() : StateScreenModel<CharacterScreenState>(CharacterScreenState.Init) {
    suspend fun fetchData() {
        mutableState.value = CharacterScreenState.CharacterContent(
            character = Character(
                id = 1,
                image = "das",
                name = "Rick",
                type = "",
                status = "Alive",
                species = "Human",
                gender = "Male",
                origin = SimpleLocation(
                    name = "Earth",
                    url = "null"
                ),
                location = SimpleLocation(
                    name = "",
                    url = "null"
                ),
                episodes = listOf("1", "2"),
                url = "url"
            )
        )
    }
}