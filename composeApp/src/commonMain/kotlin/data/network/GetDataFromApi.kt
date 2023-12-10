package models

import data.Character
import data.Episode
import data.Location
import data.network.ApiRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class GetDataFromApi(
    private val httpClient: HttpClient,
    private val apiCharacterMapper: ApiCharacterMapper,
    private val apiLocationMapper: ApiLocationMapper,
    private val apiEpisodeMapper: ApiEpisodeMapper,
) : ApiRepository {
    override suspend fun getCharactersData(): List<Character> =
        apiCharacterMapper.map(
            (httpClient.get("https://rickandmortyapi.com/api/character")
                .body<ApiCharactersResponse>()).results
        )

    override suspend fun getLocationsData(): List<Location> =
        apiLocationMapper.map(
            (httpClient.get("https://rickandmortyapi.com/api/character")
                .body<ApiLocationsResponse>()).results
        )

    override suspend fun getEpisodesData(): List<Episode> =
        apiEpisodeMapper.map(
            (httpClient.get("https://rickandmortyapi.com/api/character")
                .body<ApiEpisodesResponse>()).results
        )
}