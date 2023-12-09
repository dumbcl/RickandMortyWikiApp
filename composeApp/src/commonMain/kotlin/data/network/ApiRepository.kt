package data.network

import data.Character
import data.Episode
import data.Location

interface ApiRepository {
    suspend fun getCharactersData(): List<Character>

    suspend fun getLocationsData(): List<Location>

    suspend fun getEpisodesData(): List<Episode>
}