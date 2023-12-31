package data

import data.network.ApiResultState
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): List<Character>
    fun getLocations(): List<Location>
    fun getEpisodes(): List<Episode>
    suspend fun getCharacter(id: Int): Character?
    suspend fun getLocation(id: Int): Location?
    suspend fun getEpisode(id: Int): Episode?
    suspend fun getCharactersByName(name: String): List<Character>
    suspend fun getLocationsByName(name: String): List<Location>
    suspend fun getEpisodesByName(name: String): List<Episode>
    suspend fun loadCharacters(): Flow<ApiResultState>
    suspend fun loadLocations(): Flow<ApiResultState>
    suspend fun loadEpisodes(): Flow<ApiResultState>
}