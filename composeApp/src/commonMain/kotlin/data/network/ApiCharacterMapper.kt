package models

import data.Mapper
import data.Character
import data.SimpleLocation

class ApiCharacterMapper : Mapper<ApiCharacter, Character>() {
    override fun map(model: ApiCharacter): Character {
        return Character(
            id = model.id,
            name = model.name,
            status = model.status,
            species = model.species,
            type = model.type ?: "",
            gender = model.gender,
            origin = ApiSmLocationMapper(model.origin),
            location = ApiSmLocationMapper(model.location),
            image = model.image,
            episodes = model.episode,
            url = model.url
        )
    }

    fun ApiSmLocationMapper(model: ApiSmLocation): SimpleLocation {
        return SimpleLocation(
            name = model.name,
            url = model.url
        )
    }

    override fun inverseMap(model: Character): ApiCharacter {
        TODO("Not yet implemented")
    }
}