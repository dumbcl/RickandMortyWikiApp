package data

import data.network.ApiRepository
import data.network.ApiResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update

class RickAndMortyRepositoryImpl(
    private val apiRepository: ApiRepository
): RickAndMortyRepository {
    private val characters: MutableList<Character> = mutableListOf()
    private val locations: MutableList<Location> = mutableListOf()
    private val episodes: MutableList<Episode> = mutableListOf()


    override suspend fun loadCharacters() = flow {
        try {
            val result = apiRepository.getCharactersData()
            setCharacters(result)
            emit(ApiResultState.OnSuccess(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResultState.OnFailure(e.message ?: "Failed to load characters"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun loadLocations() = flow {
        try {
            val result = apiRepository.getLocationsData()
            setLocations(result)
            emit(ApiResultState.OnSuccess(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResultState.OnFailure(e.message ?: "Failed to load locations"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun loadEpisodes() = flow {
        try {
            val result = apiRepository.getEpisodesData()
            setEpisodes(result)
            emit(ApiResultState.OnSuccess(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResultState.OnFailure(e.message ?: "Failed to load episodes"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCharacters(): List<Character> = characters

    override fun getLocations(): List<Location> = locations

    override fun getEpisodes(): List<Episode> = episodes

    override suspend fun getCharacter(id: Int): Character? = characters.firstOrNull { it.id == id }

    override suspend fun getLocation(id: Int): Location? = locations.firstOrNull { it.id == id }

    override suspend fun getEpisode(id: Int): Episode? = episodes.firstOrNull { it.id == id }

    override suspend fun getCharactersByName(name: String): List<Character> = characters.filter {it.name.startsWith(name, true)}

    override suspend fun getLocationsByName(name: String): List<Location> = locations.filter {it.name.startsWith(name, true)}

    override suspend fun getEpisodesByName(name: String): List<Episode> = episodes.filter {it.name.startsWith(name, true)}

    private fun setCharacters(loadedCharacters: List<Character>) {
        characters.addAll(loadedCharacters)
    }

    private fun setLocations(loadedLocations: List<Location>) {
        locations.addAll(loadedLocations)
    }

    private fun setEpisodes(loadedEpisodes: List<Episode>) {
        episodes.addAll(loadedEpisodes)
    }

}