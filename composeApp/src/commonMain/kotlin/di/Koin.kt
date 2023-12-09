package di

import data.RickAndMortyRepository
import data.RickAndMortyRepositoryImpl
import data.network.ApiRepository
import data.network.ApiRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ui.character_screen.CharacterScreenModel
import ui.episode_screen.EpisodeScreenModel
import ui.main_screen.MainScreenModel


fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelsModule,
            repositoryModule,
            platformModule()
        )
    }

val viewModelsModule = module {
    factory { MainScreenModel(repository = get()) }
    factory { CharacterScreenModel(repository = get()) }
    factory { EpisodeScreenModel(repository = get()) }
    //factory { LocationScreenModel() }
}

val repositoryModule = module {
    single<ApiRepository> { ApiRepositoryImpl() }
    single<RickAndMortyRepository> { RickAndMortyRepositoryImpl(apiRepository = get()) }
}

fun initKoin() = initKoin {}



