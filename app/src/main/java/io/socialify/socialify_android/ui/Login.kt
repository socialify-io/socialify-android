package io.socialify.socialify_android.ui

import android.content.res.Resources
import android.icu.text.CaseMap
import android.widget.EditText
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.theme.MaterialComponentsViewInflater
import io.socialify.socialify_android.R
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme

@Composable
fun Login(navController: NavController) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

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
            fontSize = 34.sp
        )

        Text(
            stringResource(R.string.login_subtitle),
            fontSize = 20.sp
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
                label = { Text(stringResource(R.string.login_username)) },
                maxLines = 1,
                modifier = Modifier
                    .width(350.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { it ->
                    password = it
                },
                label = { Text(stringResource(R.string.login_password)) },
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
                stringResource(R.string.login_create_account_one),
                fontSize = 15.sp
            )

            Text(
                stringResource(id = R.string.login_crete_account_two),
                fontSize = 15.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .clickable(onClick = {
                        navController.navigate("register")
                    })
                    .padding(start = 4.dp)
            )
        }

        Spacer(Modifier.height(200.dp))

        Column() {
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .width(325.dp)
            ) {
                Text(stringResource(R.string.login_button))
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}
