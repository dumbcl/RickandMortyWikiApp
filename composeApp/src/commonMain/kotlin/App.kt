import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.main_screen.elements.ContentType
import ui.main_screen.elements.MainScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun App() {
    MaterialTheme {

        //MainScreen()

        Navigator(
            screen = MainScreen(ContentType.DEFAULT),
        )
        { navigator ->
            ScaleTransition(navigator)
        }
    }
}