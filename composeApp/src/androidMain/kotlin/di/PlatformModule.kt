package di

import org.koin.core.module.Module
import org.koin.dsl.module
import ui.location_screen.LocationScreenModel

actual fun platformModule(): Module = module {
    factory { LocationScreenModel(repository = get()) }
}
