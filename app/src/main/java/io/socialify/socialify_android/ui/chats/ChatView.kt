package io.socialify.socialify_android.ui.chats

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.socialify.socialify_android.R
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme

@Composable
fun ChatView(navController: NavController) {
    var messageText by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { androidx.compose.material3.Text(
                    "Jakiś chat",
                    color = SocialifyandroidTheme.colors.text
                ) },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = { navController.navigate("content") }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back_icon_button),
                            tint = SocialifyandroidTheme.colors.text
                        )
                    }
                },

                actions = {
                    androidx.compose.material3.IconButton(onClick = { /* doSomething() */ }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search),
                            tint = SocialifyandroidTheme.colors.text
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = SocialifyandroidTheme.colors.surface)
            )
        },
        content = {
            Text("Blablalba")
        },
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 10.dp),
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        //.size(30.dp)
                        .padding(vertical = 28.dp),
                    shape = RoundedCornerShape(8.dp),
                    value = messageText,
                    onValueChange = { it ->
                        messageText = it
                    },
                    placeholder = {
                        Text(
                            stringResource(R.string.message_input_placeholder),
                            color = SocialifyandroidTheme.colors.text
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                IconButton(
                    modifier = Modifier
                        .padding(vertical = 28.dp),
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = stringResource(R.string.send_message_icon_button),
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }
        }
    )
}
