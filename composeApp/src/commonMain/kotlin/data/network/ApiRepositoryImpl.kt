package data.network

import data.Character
import data.Episode
import data.Location
import data.SimpleLocation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import models.ApiCharacter
import models.ApiCharacterMapper
import models.ApiCharactersResponse
import models.ApiEpisode
import models.ApiEpisodeMapper
import models.ApiEpisodesResponse
import models.ApiLocation
import models.ApiLocationMapper
import models.ApiLocationsResponse
import java.lang.IllegalArgumentException

class ApiRepositoryImpl(
    private val httpClient: HttpClient,
    private val apiCharacterMapper: ApiCharacterMapper,
    private val apiLocationMapper: ApiLocationMapper,
    private val apiEpisodeMapper: ApiEpisodeMapper,
): ApiRepository {
    suspend fun getCharacters(): List<ApiCharacter> {
        var currentPage = 1
        val allCharacter = mutableListOf<ApiCharacter>()
        while (currentPage <= 41) {
            val apiResponse =
                httpClient.get("https://rickandmortyapi.com/api/character?page=$currentPage")
                    .body<ApiCharactersResponse>().results

            if (apiResponse.isEmpty()) {
                break
            }
            allCharacter.addAll(apiResponse)
            currentPage++
        }
        return allCharacter
    }

    override suspend fun getCharactersData(): List<Character> =
        apiCharacterMapper.map(getCharacters())

    suspend fun getLocations(): List<ApiLocation> {
        var currentPage = 1
        val allLocation = mutableListOf<ApiLocation>()
        while (currentPage <= 7) {
            val apiResponse =
                httpClient.get("https://rickandmortyapi.com/api/location?page=$currentPage")
                    .body<ApiLocationsResponse>().results

            if (apiResponse.isEmpty()) {
                break
            }
            allLocation.addAll(apiResponse)
            currentPage++
        }
        return allLocation
    }

    override suspend fun getLocationsData(): List<Location> =
        apiLocationMapper.map(getLocations())

    suspend fun getEpisode(): List<ApiEpisode> {
        var currentPage = 1
        val allEpisode = mutableListOf<ApiEpisode>()
        while (currentPage <= 3) {
            val apiResponse =
                httpClient.get("https://rickandmortyapi.com/api/episode?page=$currentPage")
                    .body<ApiEpisodesResponse>().results

            if (apiResponse.isEmpty()) {
                break
            }
            allEpisode.addAll(apiResponse)
            currentPage++
        }
        return allEpisode
    }

    override suspend fun getEpisodesData(): List<Episode> =
        apiEpisodeMapper.map(getEpisode())
}