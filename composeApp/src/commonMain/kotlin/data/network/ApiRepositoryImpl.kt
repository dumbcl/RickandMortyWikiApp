package data.network

import data.Character
import data.Episode
import data.Location
import data.SimpleLocation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import models.ApiCharacterMapper
import models.ApiCharactersResponse
import models.ApiEpisodeMapper
import models.ApiEpisodesResponse
import models.ApiLocationMapper
import models.ApiLocationsResponse
import java.lang.IllegalArgumentException

class ApiRepositoryImpl(
    private val httpClient: HttpClient,
    private val apiCharacterMapper: ApiCharacterMapper,
    private val apiLocationMapper: ApiLocationMapper,
    private val apiEpisodeMapper: ApiEpisodeMapper,
): ApiRepository {
    override suspend fun getCharactersData(): List<Character> =
        apiCharacterMapper.map(
            (httpClient.get("https://rickandmortyapi.com/api/character")
                .body<ApiCharactersResponse>()).results
        )

    override suspend fun getLocationsData(): List<Location> =
        apiLocationMapper.map(
            (httpClient.get("https://rickandmortyapi.com/api/location")
                .body<ApiLocationsResponse>()).results
        )

    override suspend fun getEpisodesData(): List<Episode> =
        apiEpisodeMapper.map(
            (httpClient.get("https://rickandmortyapi.com/api/episode")
                .body<ApiEpisodesResponse>()).results
        )
}