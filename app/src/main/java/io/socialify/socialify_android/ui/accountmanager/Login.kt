package io.socialify.socialify_android.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import android.icu.text.CaseMap
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.theme.MaterialComponentsViewInflater
import io.socialify.socialify_android.MainActivity
import io.socialify.socialify_android.R
import io.socialify.socialify_android.SharedPreference
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import io.socialify.socialifysdk.models.SdkResponse

@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(navController: NavController) {
    val context = LocalContext.current
    val sharedPreference: SharedPreference = SharedPreference(context)

    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

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
                stringResource(R.string.login_title),
                fontSize = 34.sp,
                color = SocialifyandroidTheme.colors.text
            )

            Text(
                stringResource(R.string.login_subtitle),
                fontSize = 20.sp,
                color = SocialifyandroidTheme.colors.text
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 70.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { it ->
                        username = it
                    },
                    label = {
                        Text(
                            stringResource(R.string.login_username),
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
                            stringResource(R.string.login_password),
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
                    stringResource(R.string.login_create_account_one),
                    fontSize = 15.sp,
                    color = SocialifyandroidTheme.colors.text
                )

                Text(
                    stringResource(id = R.string.login_crete_account_two),
                    fontSize = 15.sp,
                    color = SocialifyandroidTheme.colors.clickableText,
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate("register")
                        })
                        .padding(start = 4.dp)
                )
            }

            Spacer(Modifier.height(184.dp))

            Column() {
                Button(
                    onClick = {
                        val resp: SdkResponse = MainActivity.client.registerDevice(
                            username.text,
                            password.text
                        )

                        if (resp.success) {
                            sharedPreference.save("isUserLogged", true)

                            navController.navigate("content")
                        } else {
                            MaterialAlertDialogBuilder(context)
                                .setMessage(resp.error.toString())
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
                        stringResource(R.string.login_button),
                        color = SocialifyandroidTheme.colors.text
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
