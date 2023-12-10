package di

import data.RickAndMortyRepository
import data.RickAndMortyRepositoryImpl
import data.network.ApiRepository
import data.network.ApiRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import models.ApiCharacterMapper
import models.ApiEpisodeMapper
import models.ApiLocationMapper
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
            mapperModule,
            ktorModule,
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
    single<ApiRepository> { ApiRepositoryImpl(
        httpClient = get(),
        apiCharacterMapper = get(),
        apiEpisodeMapper = get(),
        apiLocationMapper = get()
    ) }
    single<RickAndMortyRepository> { RickAndMortyRepositoryImpl(apiRepository = get()) }
}

val mapperModule = module {
    factory { ApiCharacterMapper() }
    factory { ApiLocationMapper() }
    factory { ApiEpisodeMapper() }
}

val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single { "https://rickandmortyapi.com" }
}

fun initKoin() = initKoin {}



