package ui.main_screen

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Character
import data.Episode
import data.Location
import data.RickAndMortyRepository
import data.network.ApiResultState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainScreenModel(val repository: RickAndMortyRepository) :
    StateScreenModel<MainScreenState>(MainScreenState.Init) {
    var charactersOnScreen = 8
    var locationsOnScreen = 8
    var episodesOnScreen = 8

    suspend fun fetchCharactersData() {
        repository.loadCharacters().onStart {
            mutableState.value = MainScreenState.Characters(
                listOf(),
                isNetworkError = false,
                isSearchEmpty = false,
                isLoading = true
            )
        }
            .catch {
                mutableState.value = MainScreenState.Characters(
                    listOf(),
                    isNetworkError = true,
                    isSearchEmpty = false,
                    isLoading = false
                )
            }
            .collect {
                when (it) {
                    is ApiResultState.OnSuccess<*> -> {
                        mutableState.value = MainScreenState.Characters(
                            (it.data as List<Character>).take(charactersOnScreen),
                            isNetworkError = false,
                            isSearchEmpty = false,
                            isLoading = false,
                            isLoadMoreButtonVisible = charactersOnScreen < it.data.size
                        )
                    }
                    is ApiResultState.OnFailure -> {
                        mutableState.value = MainScreenState.Characters(
                            listOf(),
                            isNetworkError = true,
                            isSearchEmpty = false,
                            isLoading = false
                        )
                    }
                }
            }
    }

    suspend fun fetchLocationsData() {
        repository.loadLocations().onStart {
            mutableState.value = MainScreenState.Locations(
                listOf(),
                isNetworkError = false,
                isSearchEmpty = false,
                isLoading = true
            )
        }
            .catch {
                mutableState.value = MainScreenState.Locations(
                    listOf(),
                    isNetworkError = true,
                    isSearchEmpty = false,
                    isLoading = false
                )
            }
            .collect {
                when (it) {
                    is ApiResultState.OnSuccess<*> -> {
                        mutableState.value = MainScreenState.Locations(
                            (it.data as List<Location>).take(locationsOnScreen),
                            isNetworkError = false,
                            isSearchEmpty = false,
                            isLoading = false,
                            isLoadMoreButtonVisible = locationsOnScreen < it.data.size
                        )
                    }

                    is ApiResultState.OnFailure -> {
                        mutableState.value = MainScreenState.Locations(
                            listOf(),
                            isNetworkError = true,
                            isSearchEmpty = false,
                            isLoading = false
                        )
                    }
                }
            }
    }

    suspend fun fetchEpisodesData() {
        repository.loadEpisodes().onStart {
            mutableState.value = MainScreenState.Episodes(
                listOf(),
                isNetworkError = false,
                isSearchEmpty = false,
                isLoading = true
            )
        }
            .catch {
                mutableState.value = MainScreenState.Episodes(
                    listOf(),
                    isNetworkError = true,
                    isSearchEmpty = false,
                    isLoading = false
                )
            }
            .collect {
                when (it) {
                    is ApiResultState.OnSuccess<*> -> {
                        mutableState.value = MainScreenState.Episodes(
                            (it.data as List<Episode>).take(episodesOnScreen),
                            isNetworkError = false,
                            isSearchEmpty = false,
                            isLoading = false,
                            isLoadMoreButtonVisible = episodesOnScreen < it.data.size
                        )
                    }

                    is ApiResultState.OnFailure -> {
                        mutableState.value = MainScreenState.Episodes(
                            listOf(),
                            isNetworkError = true,
                            isSearchEmpty = false,
                            isLoading = false
                        )
                    }
                }
            }
    }

    fun changeToCharactersContent() {
        screenModelScope.launch {
            val characters = repository.getCharacters()
            if (characters.isEmpty()) {
                fetchCharactersData()
            } else
                mutableState.value = MainScreenState.Characters(
                    characters.take(charactersOnScreen),
                    false,
                    false,
                    isLoadMoreButtonVisible = charactersOnScreen < characters.size
                )
        }
    }

    fun changeToLocationsContent() {
        screenModelScope.launch {
            val locations = repository.getLocations()
            if (locations.isEmpty()) {
                fetchLocationsData()
            } else
                mutableState.value = MainScreenState.Locations(
                    locations.take(locationsOnScreen),
                    false,
                    false,
                    isLoadMoreButtonVisible = locationsOnScreen < locations.size
                )
        }
    }

    fun changeToEpisodesContent() {
        screenModelScope.launch {
            val episodes = repository.getEpisodes()
            if (episodes.isEmpty()) {
                fetchEpisodesData()
            } else
                mutableState.value = MainScreenState.Episodes(
                    episodes.take(episodesOnScreen),
                    false,
                    false,
                    isLoadMoreButtonVisible = episodesOnScreen < episodes.size
                )
        }
    }

    fun searchCharacters(name: String) {
        if (name.isNotEmpty()) {
            screenModelScope.launch {
                val characters = repository.getCharactersByName(name)
                if (characters.isEmpty()) {
                    mutableState.value = MainScreenState.Characters(
                        listOf(),
                        false,
                        true,
                    )
                } else {
                    mutableState.value = MainScreenState.Characters(
                        characters,
                        false,
                        false,
                        isLoadMoreButtonVisible = false
                    )
                }
            }
        } else {
            screenModelScope.launch {
                mutableState.value = MainScreenState.Characters(
                    repository.getCharacters(),
                    false,
                    false,
                    isLoadMoreButtonVisible = false
                )
            }
        }
    }

    fun searchEpisodes(name: String) {
        if (name.isNotEmpty()) {
            screenModelScope.launch {
                val episodes = repository.getEpisodesByName(name)
                if (episodes.isEmpty()) {
                    mutableState.value = MainScreenState.Episodes(
                        listOf(),
                        false,
                        true,
                    )
                } else {
                    mutableState.value = MainScreenState.Episodes(
                        episodes,
                        false,
                        false,
                        isLoadMoreButtonVisible = false
                    )
                }
            }
        } else {
            screenModelScope.launch {
                mutableState.value = MainScreenState.Episodes(
                    repository.getEpisodes(),
                    false,
                    false,
                    isLoadMoreButtonVisible = false
                )
            }
        }
    }

    fun searchLocations(name: String) {
        if (name.isNotEmpty()) {
            screenModelScope.launch {
                val locations = repository.getLocationsByName(name)
                if (locations.isEmpty()) {
                    mutableState.value = MainScreenState.Locations(
                        listOf(),
                        false,
                        true,
                    )
                } else {
                    mutableState.value = MainScreenState.Locations(
                        locations,
                        false,
                        false,
                        isLoadMoreButtonVisible = false
                    )
                }
            }
        } else {
            screenModelScope.launch {
                mutableState.value = MainScreenState.Locations(
                    repository.getLocations(),
                    false,
                    false,
                    isLoadMoreButtonVisible = false
                )
            }
        }
    }

    fun loadMoreCharacters() {
        charactersOnScreen += 8
        val characters = repository.getCharacters()
        mutableState.value = MainScreenState.Characters(
            characters.take(charactersOnScreen),
            false,
            false,
            isLoadMoreButtonVisible = charactersOnScreen < characters.size
        )
    }

    fun loadMoreLocations() {
        locationsOnScreen += 8
        val locations = repository.getLocations()
        mutableState.value = MainScreenState.Locations(
            locations.take(locationsOnScreen),
            false,
            false,
            isLoadMoreButtonVisible = locationsOnScreen < locations.size
        )
    }

    fun loadMoreEpisodes() {
        episodesOnScreen += 8
        val episodes = repository.getEpisodes()
        mutableState.value = MainScreenState.Episodes(
            episodes.take(episodesOnScreen),
            false,
            false,
            isLoadMoreButtonVisible = episodesOnScreen < episodes.size
        )
    }

}