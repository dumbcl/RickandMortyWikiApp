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
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class MainScreenModel(val repository: RickAndMortyRepository) :
    StateScreenModel<MainScreenState>(MainScreenState.Init) {

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
                            it.data as List<Character>,
                            isNetworkError = false,
                            isSearchEmpty = false,
                            isLoading = false
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
                            it.data as List<Location>,
                            isNetworkError = false,
                            isSearchEmpty = false,
                            isLoading = false
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
                            it.data as List<Episode>,
                            isNetworkError = false,
                            isSearchEmpty = false,
                            isLoading = false
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
            if (repository.getCharacters().isEmpty()) {
                fetchCharactersData()
            } else
                mutableState.value = MainScreenState.Characters(
                    repository.getCharacters(),
                    false,
                    false,
                )
        }
    }

    fun changeToLocationsContent() {
        screenModelScope.launch {
            if (repository.getLocations().isEmpty()) {
                fetchLocationsData()
            } else
                mutableState.value = MainScreenState.Locations(
                    repository.getLocations(),
                    false,
                    false,
                )
        }
    }

    fun changeToEpisodesContent() {
        screenModelScope.launch {
            if (repository.getEpisodes().isEmpty()) {
                fetchEpisodesData()
            } else
                mutableState.value = MainScreenState.Episodes(
                    repository.getEpisodes(),
                    false,
                    false,
                )
        }
    }

}


//        mutableState.value = MainScreenState.Characters(
//            listOf(Character(
//                id = 8377,
//                name = "Aurelia McKenzie",
//                status = "null",
//                species = "docendi",
//                type = "null",
//                gender = "null",
//                origin = SimpleLocation(
//                    name = "Vilma Watts",
//                    url = "http://www.bing.com/search?q=egestas"
//                ),
//                location = SimpleLocation(
//                    name = "Richard Salas",
//                    url = "https://search.yahoo.com/search?p=dicant"
//                ),
//                image = "detracto",
//                episodes = listOf(),
//                url = "null"
//            )),
//            isNetworkError = false,
//            isSearchEmpty = false,
//            //isLoading = true
//        )