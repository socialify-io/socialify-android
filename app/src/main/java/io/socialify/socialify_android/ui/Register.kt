package io.socialify.socialify_android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.socialify.socialify_android.R

@Composable
fun Register(navController: NavController) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var repeatedPassword by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        horizontalAlignment = Alignment .CenterHorizontally,
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
            fontSize = 34.sp
        )

        Text(
            stringResource(R.string.register_subtitle),
            fontSize = 20.sp
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
                label = { Text(stringResource(R.string.register_username)) },
                maxLines = 1,
                modifier = Modifier
                    .width(350.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { it ->
                    password = it
                },
                label = { Text(stringResource(R.string.register_password)) },
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .width(350.dp)
            )

            OutlinedTextField(
                value = repeatedPassword,
                onValueChange = { it ->
                    repeatedPassword= it
                },
                label = { Text(stringResource(R.string.register_password_repeat)) },
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .width(350.dp)
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(
                stringResource(R.string.register_login_to_account_one),
                fontSize = 15.sp
            )

            Text(
                stringResource(id = R.string.register_login_to_account_two),
                fontSize = 15.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .clickable(onClick = {
                        navController.navigate("login")
                    })
                    .padding(start = 4.dp)
            )
        }

        Spacer(Modifier.height(165.dp))

        Column() {
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .width(325.dp)
            ) {
                Text(stringResource(R.string.register_button))
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}
