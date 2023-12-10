package models

import kotlinx.serialization.Serializable

@Serializable
data class ApiLocationsResponse(
    val results: List<ApiLocation>
)

@Serializable
data class ApiLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)