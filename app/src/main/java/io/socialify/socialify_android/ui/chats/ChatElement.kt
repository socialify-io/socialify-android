package io.socialify.socialify_android.ui.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.socialify.socialify_android.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.material.*

@Preview(showBackground = false)
@Composable
fun ChatElement() {

    Surface {
        Row() {
            Image(
                painterResource(R.drawable.ic_socialify_logo),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Column() {
                Text("Nazwa")
                Text("Ostatnia wiadomość")
            }
        }
    }
}
