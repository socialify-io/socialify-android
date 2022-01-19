package io.socialify.socialify_android.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.socialify.socialify_android.MainActivity
import io.socialify.socialify_android.R
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import io.socialify.socialifysdk.data.models.SdkResponse


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Register(navController: NavController) {
    val context = LocalContext.current

    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var repeatedPassword by remember { mutableStateOf(TextFieldValue("")) }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painterResource(R.drawable.ic_socialify_logo),
                "socialify logo image",
                modifier = Modifier
                    .width(213.dp)
                    .height(218.dp)
            )
            Text(
                stringResource(R.string.register_title),
                fontSize = 34.sp,
                color = SocialifyandroidTheme.colors.text
            )

            Text(
                stringResource(R.string.register_subtitle),
                fontSize = 20.sp,
                color = SocialifyandroidTheme.colors.text
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 40.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { it ->
                        username = it
                    },
                    label = {
                        Text(
                            stringResource(R.string.register_username),
                            color = SocialifyandroidTheme.colors.text
                        )
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .width(340.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { it ->
                        password = it
                    },
                    label = {
                        Text(
                            stringResource(R.string.register_password),
                            color = SocialifyandroidTheme.colors.text
                        )
                    },
                    maxLines = 1,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .width(340.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = repeatedPassword,
                    onValueChange = { it ->
                        repeatedPassword = it
                    },
                    label = {
                        Text(
                            stringResource(R.string.register_password_repeat),
                            color = SocialifyandroidTheme.colors.text
                        )
                    },
                    maxLines = 1,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .width(340.dp)
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 15.dp)
            ) {
                Text(
                    stringResource(R.string.register_login_to_account_one),
                    fontSize = 15.sp,
                    color = SocialifyandroidTheme.colors.text
                )

                Text(
                    stringResource(id = R.string.register_login_to_account_two),
                    fontSize = 15.sp,
                    color = SocialifyandroidTheme.colors.clickableText,
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate("login")
                        })
                        .padding(start = 4.dp)
                )
            }

            Spacer(Modifier.weight(1.0f))

            Column() {
                Button(
                    onClick = {
                        val resp: SdkResponse? = MainActivity.client?.registerAccount(
                            username.text,
                            password.text,
                            repeatedPassword.text
                        )

                        val dialogText: String?

                        if (resp?.success == true) {
                            MaterialAlertDialogBuilder(context)
                                .setMessage(context.getString(R.string.register_success))
                                .setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                                    navController.navigate("login")
                                }
                                .create()
                                .show()
                        } else {
                            MaterialAlertDialogBuilder(context)
                                .setMessage(resp?.error.toString())
                                .setPositiveButton(context.getString(R.string.report)) { _, _ -> }
                                .setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
                                .create()
                                .show()
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .width(325.dp)
                ) {
                    Text(
                        stringResource(R.string.register_button),
                        color = SocialifyandroidTheme.colors.text
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
