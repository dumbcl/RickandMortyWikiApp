package data

data class Character(
    val id: Int,
    val name: String,
    var status: String?,
    val species: String,
    val type: String?,
    val gender: String?,
    val origin: SimpleLocation?,
    var location: SimpleLocation?,
    val image: String,
    val episode: List<String>?,
    val url: String?,
)

data class SimpleCharacter(
    val name: String,
    val url: String?,
)
