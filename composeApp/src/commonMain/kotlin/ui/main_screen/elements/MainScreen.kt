package ui.main_screen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.Character
import data.Episode
import data.Location
import getPlatform
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.LoadingStub
import ui.NetworkStub
import ui.Palette
import ui.character_screen.CharacterScreen
import ui.episode_screen.EpisodeScreen
import ui.getScreenModel
import ui.location_screen.LocationScreen
import ui.main_screen.MainScreenModel
import ui.main_screen.MainScreenState


const val KEY_MAIN_SCREEN = "mainScreenKey"

@OptIn(ExperimentalResourceApi::class)
class MainScreen(val contentType: ContentType) : Screen {

    override val key: ScreenKey
        get() = KEY_MAIN_SCREEN

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainScreenModel = getScreenModel<MainScreenModel>()
        val mainScreenDataState = mainScreenModel.state.collectAsState()

        if (contentType == ContentType.DEFAULT) {
            LaunchedEffect(currentCompositeKeyHash) {
                mainScreenModel.fetchAllData()
            }
        }


        when (val state = mainScreenDataState.value) {
            is MainScreenState.Init -> MainScreenContent(
                ContentType.CHARACTERS,
                listOf(),
                isNetworkError = false,
                isSearchEmpty = false,
                isLoading = true,
                isLoadMoreVisible = true,
                mainScreenModel,
                navigator
            )

            is MainScreenState.Characters -> MainScreenContent(
                ContentType.CHARACTERS,
                state.characters,
                isNetworkError = state.isNetworkError,
                isSearchEmpty = state.isSearchEmpty,
                isLoading = state.isLoading,
                isLoadMoreVisible = state.isLoadMoreButtonVisible,
                mainScreenModel,
                navigator
            )

            is MainScreenState.Episodes -> MainScreenContent(
                ContentType.EPISODES,
                state.episodes,
                isNetworkError = state.isNetworkError,
                isSearchEmpty = state.isSearchEmpty,
                isLoading = state.isLoading,
                isLoadMoreVisible = state.isLoadMoreButtonVisible,
                mainScreenModel,
                navigator
            )

            is MainScreenState.Locations -> MainScreenContent(
                ContentType.LOCATIONS,
                state.locations,
                isNetworkError = state.isNetworkError,
                isSearchEmpty = state.isSearchEmpty,
                isLoading = state.isLoading,
                isLoadMoreVisible = state.isLoadMoreButtonVisible,
                mainScreenModel,
                navigator
            )
        }
    }

    @Composable
    fun MainScreenContent(
        contentType: ContentType,
        itemsOnScreen: List<Any>,
        isNetworkError: Boolean,
        isSearchEmpty: Boolean,
        isLoading: Boolean,
        isLoadMoreVisible: Boolean,
        mainScreenModel: MainScreenModel,
        navigator: Navigator,
    ) {
        //Android or Java
        val platformName = getPlatform().name.split(" ")[0]
        val isAndroid = platformName == "Android"
        val horizontalPadding = if (isAndroid) 25.dp else 100.dp

        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val openMenu: () -> Unit = {
            scope.launch {
                drawerState.open()
            }
        }

        val closeMenu: () -> Unit = {
            scope.launch {
                drawerState.close()
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Palette.BackgroundColor),
            backgroundColor = Palette.BackgroundColor,
            drawerContent = {
                SideMenu(
                    open = drawerState.isOpen,
                    onDismiss = closeMenu,
                    onMenuItemClick = { menuItem ->
                        when (menuItem) {
                            "Characters" -> mainScreenModel.changeToCharactersContent()
                            "Locations" -> mainScreenModel.changeToLocationsContent()
                            "Episodes" -> mainScreenModel.changeToEpisodesContent()
                        }
                    },
                    contentType = contentType,
                )
            },
            //drawerAlignment = Alignment.Start,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontalPadding, 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (isAndroid) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                    ){
                        IconButton(
                            onClick = openMenu,
                        ) {
                            Icon(
                                Icons.Default.List,
                                "choose items type",
                                tint = Palette.DetailsTextColor,
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(48.dp),
                            )
                        }
                    }

                } else {
                    Row() {
                        Text(
                            modifier = Modifier.clickable(onClick = { mainScreenModel.changeToCharactersContent() }),
                            text = "Characters",
                            color = if (contentType == ContentType.CHARACTERS) Palette.GeneralTextColor else Palette.DetailsTextColor,
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                            ),
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            modifier = Modifier.clickable(onClick = { mainScreenModel.changeToLocationsContent() }),
                            text = "Locations",
                            color = if (contentType == ContentType.LOCATIONS) Palette.GeneralTextColor else Palette.DetailsTextColor,
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                            ),
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            modifier = Modifier.clickable(onClick = { mainScreenModel.changeToEpisodesContent() }),
                            text = "Episodes",
                            color = if (contentType == ContentType.EPISODES) Palette.GeneralTextColor else Palette.DetailsTextColor,
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                            ),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painterResource("image_rick_and_morty_logo.xml"),
                    null
                )

                if (isLoading) {
                    LoadingStub()
                }
                else if (isNetworkError) {
                    NetworkStub {
                        when (contentType) {
                            ContentType.CHARACTERS -> scope.launch { mainScreenModel.fetchCharactersData() }
                            ContentType.LOCATIONS -> scope.launch { mainScreenModel.fetchLocationsData() }
                            ContentType.EPISODES -> scope.launch { mainScreenModel.fetchEpisodesData() }
                            ContentType.DEFAULT -> scope.launch { mainScreenModel.fetchCharactersData() }
                        }
                    }
                } else {

                    Spacer(modifier = Modifier.height(45.dp))
                    var title = when (contentType) {
                        ContentType.CHARACTERS -> "Characters"
                        ContentType.LOCATIONS -> "Locations"
                        ContentType.EPISODES -> "Episodes"
                        ContentType.DEFAULT -> "Characters"
                    }
                    var queryHint = when (contentType) {
                        ContentType.CHARACTERS -> "Find a character"
                        ContentType.LOCATIONS -> "Find a location"
                        ContentType.EPISODES -> "Find an episode"
                        ContentType.DEFAULT -> "Find a character"
                    }
                    var query by remember { mutableStateOf("") }
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = query,
                        onValueChange = { newQuery ->
                            query = newQuery
                        },
                        placeholder = {
                            Text(
                                queryHint,
                                color = Palette.GeneralTextColor
                            )
                        },
                        textStyle = TextStyle(
                            color = Palette.GeneralTextColor,
                            fontSize = 22.sp,
                            fontWeight = FontWeight(400),
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Palette.GeneralTextColor,
                            backgroundColor = Palette.SecondaryCardsColor,
                            trailingIconColor = Palette.GeneralTextColor
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = { Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                when (contentType) {
                                    ContentType.CHARACTERS -> mainScreenModel.searchCharacters(query)
                                    ContentType.LOCATIONS -> mainScreenModel.searchLocations(query)
                                    ContentType.EPISODES -> mainScreenModel.searchEpisodes(query)
                                    ContentType.DEFAULT -> mainScreenModel.searchCharacters(query)
                                }
                            }
                        ) }
                    )

                    if (isSearchEmpty) {
                        SearchStub()
                    } else {

                        Spacer(modifier = Modifier.height(35.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = title,
                                color = Palette.GeneralTextColor,
                                style = TextStyle(
                                    color = Palette.GeneralTextColor,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight(700),
                                ),
                            )
                            Spacer(modifier = Modifier.width(25.dp))
                            Divider(
                                color = Palette.DetailsTextColor,
                                thickness = 3.dp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (isAndroid) {
                            LazyColumn(
                                contentPadding = PaddingValues(30.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                items(items = itemsOnScreen) { item ->
                                    when (item) {
                                        is Character -> UICharacterItem(name = item.name, species = item.species, image = item.image, onClick = { navigator.push(CharacterScreen(item.id)) })
                                        is Location -> UILocationItem(name = item.name, type = item.type, onClick = { navigator.push(
                                            LocationScreen(item.id)
                                        ) })
                                        is Episode -> UIEpisodeItem(name = item.name, date = item.airDate, code = item.code, onClick = { navigator.push(
                                            EpisodeScreen(item.id)
                                        ) })
                                    }
                                }
                                if (isLoadMoreVisible) item{LoadMoreButton {
                                    when (contentType) {
                                        ContentType.CHARACTERS -> {
                                            mainScreenModel.loadMoreCharacters()
                                        }
                                        ContentType.DEFAULT -> {
                                            mainScreenModel.loadMoreCharacters()
                                        }
                                        ContentType.LOCATIONS -> {
                                            mainScreenModel.loadMoreLocations()
                                        }
                                        ContentType.EPISODES -> {
                                            mainScreenModel.loadMoreEpisodes()
                                        }
                                    }
                                }
                                }
                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(4),
                                contentPadding = PaddingValues(60.dp),
                                horizontalArrangement = Arrangement.Center
                                ) {
                                items(items = itemsOnScreen) { item ->
                                    when (item) {
                                        is Character -> UICharacterItem(
                                            name = item.name,
                                            species = item.species,
                                            image = item.image,
                                            onClick = { navigator.push(CharacterScreen(item.id)) })

                                        is Location -> UILocationItem(
                                            name = item.name,
                                            type = item.type,
                                            onClick = { navigator.push(LocationScreen(item.id)) })

                                        is Episode -> UIEpisodeItem(
                                            name = item.name,
                                            date = item.airDate,
                                            code = item.code,
                                            onClick = { navigator.push(EpisodeScreen(item.id)) })
                                    }
                                }
                                if (isLoadMoreVisible) item {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Spacer(modifier = Modifier.height(30.dp))
                                        LoadMoreButton {
                                            when (contentType) {
                                                ContentType.CHARACTERS -> {
                                                    mainScreenModel.loadMoreCharacters()
                                                }

                                                ContentType.DEFAULT -> {
                                                    mainScreenModel.loadMoreCharacters()
                                                }

                                                ContentType.LOCATIONS -> {
                                                    mainScreenModel.loadMoreLocations()
                                                }

                                                ContentType.EPISODES -> {
                                                    mainScreenModel.loadMoreEpisodes()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }


                    }
                }
            }
        }
    }

    @Composable
    fun SearchStub(){
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Not found",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight(700),
                color = Palette.DetailsTextColor,
                textAlign = TextAlign.Center,
            )
        )
    }

    @Composable
    fun LoadMoreButton(
        onClick: () -> Unit
    ){
        TextButton(
            onClick = onClick,
            modifier = Modifier
                .width(218.dp)
                .height(81.dp)
                .background(
                    color = Palette.PrimaryCardsColor,
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .border(
                    width = 3.dp,
                    color = Palette.DetailsTextColor,
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                ),
        ) {
            Text(
                text = "Load more...",
                color = Palette.DetailsTextColor,
                style = TextStyle(
                    color = Palette.DetailsTextColor,
                    fontSize = 26.sp,
                    fontWeight = FontWeight(700),
                ),
            )
        }
    }

    @Composable
    fun SideMenu(
        open: Boolean,
        onDismiss: () -> Unit,
        onMenuItemClick: (String) -> Unit,
        contentType: ContentType
    ) {
        if (open) {
            Popup(
                alignment = Alignment.TopStart,
                onDismissRequest = onDismiss
            ) {

                Box(
                    modifier = Modifier.width(250.dp)
                        .fillMaxHeight()
                        .background(Palette.SecondaryCardsColor),
                    //color = Palette.SecondaryCardsColor,
                ) {
                    Column() {
                        Image(
                            painterResource("image_rick_and_morty_logo.xml"),
                            null,
                            modifier = Modifier.width(200.dp).height(100.dp).padding(20.dp, 0.dp)
                        )
                        Text(
                            modifier = Modifier.clickable {
                                onMenuItemClick("Characters")
                                onDismiss()
                            }.padding(20.dp, 0.dp),
                            text = "Characters",
                            color = if (contentType == ContentType.CHARACTERS) Palette.GeneralTextColor else Palette.DetailsTextColor,
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                            ),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.clickable {
                                onMenuItemClick("Locations")
                                onDismiss()
                            }.padding(20.dp, 0.dp),
                            text = "Locations",
                            color = if (contentType == ContentType.LOCATIONS) Palette.GeneralTextColor else Palette.DetailsTextColor,
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                            ),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.clickable {
                                onMenuItemClick("Episodes")
                                onDismiss()
                            }.padding(20.dp, 0.dp),
                            text = "Episodes",
                            color = if (contentType == ContentType.EPISODES) Palette.GeneralTextColor else Palette.DetailsTextColor,
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight(700),
                            ),
                        )
                    }
                }
            }
        }
    }


}

enum class ContentType {
    DEFAULT, CHARACTERS, EPISODES, LOCATIONS
}
