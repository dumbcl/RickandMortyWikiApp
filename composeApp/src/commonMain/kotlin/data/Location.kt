package data

data class SimpleLocation(val name: String, val url: String)

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String?,
    val residents: List<String>?,
    val url: String?,
)
