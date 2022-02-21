package io.socialify.socialify_android.ui.chats

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.socialify.socialify_android.MainActivity
import io.socialify.socialify_android.MainActivity.Companion.socketClient
import io.socialify.socialify_android.R
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import org.json.JSONArray

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun SearchView(navController: NavController) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var isSearchBarFocused: Boolean = false
    var activity = LocalContext.current as Activity

    Scaffold(
        containerColor = MaterialTheme.colors.background,
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                  IconButton(onClick = { navController.navigate("content") }) {
                      Icon(
                          imageVector = Icons.Filled.ArrowBack,
                          contentDescription = stringResource(R.string.go_back_icon_button),
                          tint = SocialifyandroidTheme.colors.text
                      )
                  }
                },
                title = {
                    BasicTextField(
                        modifier = Modifier
                            .height(42.dp)
                            .fillMaxWidth()
                            .padding(end = 12.dp)
                            .background(SocialifyandroidTheme.colors.gray,
                            RoundedCornerShape(8.dp)),
                    value = searchText,
                        onValueChange = { it ->
                            searchText = it
                            socketClient?.findUser(searchText.text)
                        },
                        singleLine= true,
                        maxLines = 1,
                        textStyle = TextStyle(color = SocialifyandroidTheme.colors.text,
                            fontSize = 18.sp,
                           baselineShift = BaselineShift(-0.5F)
                        )
                    )
//                    TextField(
//                        modifier = Modifier
//                            .height(50.dp)
//                            .sizeIn(50.dp)
//                            .padding(0.dp),
//                        shape = RoundedCornerShape(8.dp),
//                        value = searchText,
//                        onValueChange = { it ->
//                            searchText = it
//                        },
//                        placeholder = {
//                            Text(
//                                stringResource(R.string.search)
//                            )
//                        },
//                        colors = TextFieldDefaults.textFieldColors(
//                            focusedIndicatorColor = Color.Transparent,
//                            unfocusedIndicatorColor = Color.Transparent
//                        ),
//                        singleLine = true
//                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = SocialifyandroidTheme.colors.surface)
            )
        },
        content = {
            var searchResults by remember { mutableStateOf(JSONArray()) }

            socketClient?.socket?.on("find_user") { args ->
                Log.e("find_userv", args.toString());
                searchResults = args[0] as JSONArray
            }

            Column() {
                for (i in 0 until searchResults.length()) {
                    val result = searchResults.getJSONObject(i)

                    Box(modifier = Modifier
                        .clickable {
                            MainActivity.receiverID = i + 1;
                            MainActivity.receiverName = result["username"].toString();
                            navController.navigate("chat")
                        }) {
                        androidx.compose.material.Surface {
                            Column() {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier
                                        .background(MaterialTheme.colors.background)
                                        .fillMaxWidth()
                                ) {
                                    Image(
                                        painterResource(R.drawable.ic_socialify_logo),
                                        contentDescription = "avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(68.dp)
                                            .clip(CircleShape)
                                    )

                                    Text(
                                        result["username"].toString(),
                                        color = SocialifyandroidTheme.colors.text,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Divider(
                                    color = SocialifyandroidTheme.colors.gray,
                                    modifier = Modifier.padding(horizontal = 18.dp),
                                    thickness = 0.5.dp
                                )
                            }
                        }
                    }
                }
            }
        },
    )
}

