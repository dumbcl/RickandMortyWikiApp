import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import data.SimpleCharacter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.episode_screen.EpisodeEvents
import ui.episode_screen.EpisodeScreen
import ui.episode_screen.EpisodeState
import ui.location_screen.LocationEvents
import ui.location_screen.LocationScreen
import ui.location_screen.LocationState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
//        CharacterScreen(
//            uiState = CharacterState(
//                image = "",
//                name = "Morty",
//                status = "Alive",
//                species = "Human",
//                gender = "Male",
//                origin = SimpleLocation(name = "Earth", url = ""),
//                location = SimpleLocation(name = "Earth", url = ""),
//                episodes = listOf(
//                    SimpleEpisode("S01E01", ""),
//                    SimpleEpisode("S01E01", ""),
//                    SimpleEpisode("S01E01", "")
//                )
//            ),
//            uiEvent = Channel<CharacterEvents>().receiveAsFlow(),
//            uiAction = {},
//        )
//        EpisodeScreen(
//            uiState = EpisodeState(
//                name = "Pilot",
//                airDate = "12 october 2013",
//                code = "S01E01",
//                characters = listOf(SimpleCharacter("Morty", ""), SimpleCharacter("Rick", ""), SimpleCharacter("Morty", ""))
//            ),
//            uiEvent = Channel<EpisodeEvents>().receiveAsFlow(),
//            uiAction = {}
//        )
        LocationScreen(
            uiState = LocationState(
                name = "Earth",
                type = "Planet",
                dimension = "C3567",
                residents = listOf(SimpleCharacter("Morty", ""), SimpleCharacter("Rick", ""), SimpleCharacter("Morty", ""))
            ),
            uiEvent = Channel<LocationEvents>().receiveAsFlow(),
            uiAction = {}
        )
    }
}