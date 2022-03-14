package io.socialify.socialify_android.ui.chats

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.socialify.socialify_android.MainActivity
import io.socialify.socialify_android.MainActivity.Companion.socketClient
import io.socialify.socialify_android.R
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import io.socialify.socialifysdk.data.db.entities.Account
import io.socialify.socialifysdk.data.db.entities.DM
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatView(navController: NavController) {
    var messageText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    var receiverId = MainActivity.receiverID
    var username = MainActivity.receiverName

    var allMessages: List<DM>? = listOf()
    var allMessagesLiveData: LiveData<List<DM>>? = null

    var accounts: List<Account>? = null

    GlobalScope.launch {
        accounts = socketClient?.db?.accountDao?.getAll()

        Log.e("ACCOUNTID", accounts?.get(0)!!.userId.toString())
        Log.e("RECEIVERID", receiverId!!.toString())

        allMessagesLiveData =
            socketClient?.db?.dmDao?.getDMs(
                account = accounts?.get(0)!!.userId,
                receiver = receiverId!!
            )
        Log.e("DUPA", allMessagesLiveData.toString())
    }

//    allMessagesLiveData?.observeForever() {
//        allMessages.
//    }

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
            socketClient?.socket?.on("send_dm") { args ->
                if (args[0] != null) {
                    var response = args[0] as JSONObject
                    Log.e("NOWY DM", response.toString())

                    val newDM = DM(
                        id       = response["id"].toString().toLong(),
                        userId   = accounts?.get(0)!!.userId,
                        receiver = response["receiverId"].toString().toLong(),
                        sender   = response["senderId"].toString().toLong(),
                        message  = response["message"] as String,
                        username = response["username"] as String,
                        isRead   = true,
                        date     = response["date"].toString()
                    )

                    GlobalScope.launch {
                       socketClient?.db?.dmDao?.insertAll(dm = newDM)
                    }
                }
            }

//            allMessages?.forEach() { message ->
//                Text(message.toString())
//            }

            //Text(allMessages.toString())

            val items by allMessagesLiveData!!.observeAsState()
            Log.e("DUPA", items.toString())

            Column {
                //item {
                    items?.forEach { message ->
                        Text("<${message.username}>: ${message.message}")
                    }
               // }
            }
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
