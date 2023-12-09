import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.javafx.JavaFxDispatcher
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.swing.Swing
import kotlinx.coroutines.swing.SwingDispatcher
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin

//lateinit var koin: Koin
@OptIn(ExperimentalCoroutinesApi::class)
fun main() = application {
    Dispatchers.setMain(Dispatchers.Default)
    initKoin {}
    Window(
        title = "Rick and Morty Wiki",
        state = rememberWindowState(width = 1400.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}