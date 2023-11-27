package data

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val code: String,
    val characters: List<String>?,
    val url: String?,
)

data class SimpleEpisode(
    val code: String,
    val url: String?,
)
