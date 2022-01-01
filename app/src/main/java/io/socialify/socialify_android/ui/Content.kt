package io.socialify.socialify_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import io.socialify.socialify_android.ui.chats.ChatElement
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import java.lang.reflect.Modifier

@ExperimentalMaterial3Api
@Preview(showBackground = false)
@Composable
fun Content() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Chats", "More")
    val icons = listOf(Icons.Filled.ChatBubble, Icons.Filled.More)

    Scaffold(
        containerColor = MaterialTheme.colors.background,
        topBar = {
            SmallTopAppBar(
                title = { Text(
                    items[selectedItem],
                    color = SocialifyandroidTheme.colors.text
                ) },
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
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add new chat",
                            tint = SocialifyandroidTheme.colors.text
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = SocialifyandroidTheme.colors.surface)
            )
        },

        content = {
            Column() {
                ChatElement()
                ChatElement()
                ChatElement()
                ChatElement()
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

