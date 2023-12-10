package models

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ApiCharactersResponse(
    val results: List<ApiCharacter>
)

@Serializable
data class ApiCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: ApiSmLocation,
    val location: ApiSmLocation,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@Serializable
data class ApiSmLocation(
    val name: String,
    val url: String
)
