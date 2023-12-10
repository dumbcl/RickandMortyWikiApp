package data.network

import data.Character
import data.Episode
import data.Location
import data.SimpleLocation
import java.lang.IllegalArgumentException

class ApiRepositoryImpl: ApiRepository {
    override suspend fun getCharactersData(): List<Character> {
        return (listOf(Character(
            id = 1,
            name = "Darrell Bradley",
            status = "postea",
            species = "epicurei",
            type = "deterruisset",
            gender = null,
            origin = SimpleLocation(
                name = "Fredrick Porter",
                url = "https://search.yahoo.com/search?p=periculis"
            ),
            location = SimpleLocation(
                name = "Gonzalo Hardy",
                url = "https://search.yahoo.com/search?p=veri"
            ),
            image = "audire",
            episodes = listOf(),
            url = null
        ),
            Character(
                id = 2153,
                name = "Kim Knowles",
                status = "veri",
                species = "latine",
                type = "fastidii",
                gender = null,
                origin = SimpleLocation(
                    name = "Guadalupe McPherson",
                    url = "https://www.google.com/#q=fabellas"
                ),
                location = SimpleLocation(
                    name = "Allan Molina",
                    url = "http://www.bing.com/search?q=prodesset"
                ),
                image = "habemus",
                episodes = listOf(),
                url = null
            )
        ))
        //TODO("Not yet implemented")
    }

    override suspend fun getLocationsData(): List<Location> {
        return (listOf(Location(
            id = 1,
            name = "Tisha Hawkins",
            type = "pulvinar",
            dimension = null,
            residents = listOf(),
            url = null
        )))
    }

    override suspend fun getEpisodesData(): List<Episode> {
        return (listOf(Episode(
            id = 1,
            name = "Reynaldo Lloyd",
            airDate = "consectetuer",
            code = "vix",
            characters = listOf(),
            url = null
        )))
    }
}