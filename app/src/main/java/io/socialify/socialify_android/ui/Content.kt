package io.socialify.socialify_android.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.socialify.socialify_android.MainActivity.Companion.socketClient
import io.socialify.socialify_android.ui.chats.ChatListElement
import io.socialify.socialify_android.ui.chats.ChatView
import io.socialify.socialify_android.ui.chats.SearchView
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme

@SuppressLint("ComposableDestinationInComposeScope")
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Preview(showBackground = false)
@Composable
fun Content() {
    val context = LocalContext.current
    val navController = rememberNavController()
    socketClient?.establishConnection()

    NavHost(navController = navController, startDestination = "content") {
        composable("content") {
            var selectedItem by remember { mutableStateOf(0) }
            val items = listOf("Chats", "More")
            val icons = listOf(Icons.Filled.ChatBubble, Icons.Filled.More)

            Scaffold(
                containerColor = MaterialTheme.colors.background,
                topBar = {
                    SmallTopAppBar(
                        title = {
                            Text(
                                items[selectedItem],
                                color = SocialifyandroidTheme.colors.text
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "Open account manager",
                                    tint = SocialifyandroidTheme.colors.text
                                )
                            }
                        },

                        actions = {
                            IconButton(onClick = {
                               navController.navigate("search")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = SocialifyandroidTheme.colors.text
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = SocialifyandroidTheme.colors.surface)
                    )
                },

                content = {

                    Column() {
                        Box(modifier = Modifier
                            .clickable { navController.navigate("chat") }) {
                            ChatListElement()
                        }

                        Box(modifier = Modifier
                            .clickable { navController.navigate("chat") }) {
                            ChatListElement()
                        }

                        Box(modifier = Modifier
                            .clickable { navController.navigate("chat") }) {
                            ChatListElement()
                        }

                        Box(modifier = Modifier
                            .clickable { navController.navigate("chat") }) {
                            ChatListElement()
                        }
                    }
                },

                floatingActionButton = {
                    FloatingActionButton(
                        containerColor = SocialifyandroidTheme.colors.onSurface,
                        onClick = { }
                    ) {
                        Icon(
                            Icons.Filled.Add, "Add new chat",
                            tint = SocialifyandroidTheme.colors.text
                        )
                    }
                },

                bottomBar = {
                    NavigationBar(
                        containerColor = SocialifyandroidTheme.colors.surface
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(icons[index], contentDescription = item) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = { selectedItem = index },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = SocialifyandroidTheme.colors.onSurface,
                                    unselectedTextColor = SocialifyandroidTheme.colors.text,
                                    selectedTextColor = SocialifyandroidTheme.colors.text,
                                    selectedIconColor = SocialifyandroidTheme.colors.text,
                                    unselectedIconColor = SocialifyandroidTheme.colors.text
                                )
                            )
                        }
                    }
                }
            )
        }
        composable("chat") { ChatView(navController = navController) }
        composable("search") { SearchView(navController = navController) }
    }
}

