package models

import data.Episode
import data.Mapper
import data.SimpleLocation

class ApiEpisodeMapper : Mapper<ApiEpisode, Episode>() {
    override fun map(model: ApiEpisode): Episode{
        return Episode(
            id = model.id,
            name = model.name,
            airDate = model.air_date,
            code = model.episode,
            characters = model.characters,
            url = model.url
        )
    }

    override fun inverseMap(model: Episode): ApiEpisode {
        TODO("Not yet implemented")
    }
}