package io.socialify.socialify_android.ui.chats

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.socialify.socialify_android.MainActivity
import io.socialify.socialify_android.R
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import io.socialify.socialifysdk.data.models.SdkResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatView(navController: NavController) {
    var messageText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    var receiverId = MainActivity.receiverID;
    var username = MainActivity.receiverName;

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { androidx.compose.material3.Text(
                    username.toString(),
                    color = SocialifyandroidTheme.colors.text
                ) },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = {
                        navController.navigate("content");
                    }) {
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
                    onClick = {

                        if (receiverId != null) {
                            if (messageText.text != "") {
                                MainActivity.socketClient?.sendDM(
                                    messageText.text,
                                    receiverId
                                )
                            }
                        } else {
                            MaterialAlertDialogBuilder(context)
                                .setMessage("There was an error while sending this message.")
                                .setPositiveButton(context.getString(R.string.report)) { _, _ -> navController.navigate("content"); }
                                .setNegativeButton(context.getString(R.string.cancel)) { _, _ -> navController.navigate("content"); }
                                .create()
                                .show()
                        }

                        messageText = TextFieldValue("");

                    }
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
