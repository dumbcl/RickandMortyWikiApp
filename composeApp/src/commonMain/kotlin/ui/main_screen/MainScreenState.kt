package ui.main_screen

import data.Episode
import data.Location
import data.Character
import data.SimpleEpisode
import data.SimpleLocation

sealed class MainScreenState {
    data object Init: MainScreenState()
    data class Characters(
        var characters: List<Character>,
        var isNetworkError: Boolean,
        var isSearchEmpty: Boolean,
        var isLoading: Boolean = false,
        var isLoadMoreButtonVisible: Boolean = true,
    ): MainScreenState()
    data class Episodes(
        var episodes: List<Episode>,
        var isNetworkError: Boolean,
        var isSearchEmpty: Boolean,
        var isLoading: Boolean = false,
        var isLoadMoreButtonVisible: Boolean = true,
    ): MainScreenState()
    data class Locations(
        var locations: List<Location>,
        var isNetworkError: Boolean,
        var isSearchEmpty: Boolean,
        var isLoading: Boolean = false,
        var isLoadMoreButtonVisible: Boolean = true,
    ): MainScreenState()

}