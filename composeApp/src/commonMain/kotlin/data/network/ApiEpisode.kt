package models

import kotlinx.serialization.Serializable

@Serializable
data class ApiEpisodesResponse(
    val results: List<ApiEpisode>
)

@Serializable
data class ApiEpisode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)