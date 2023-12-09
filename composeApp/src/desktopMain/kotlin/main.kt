import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoin
import org.koin.core.context.startKoin

//lateinit var koin: Koin
fun main() = application {
    initKoin {}
    Window(
        title = "Rick and Morty Wiki",
        state = rememberWindowState(width = 1400.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}