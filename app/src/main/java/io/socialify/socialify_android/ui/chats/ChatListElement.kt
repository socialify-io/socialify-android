package io.socialify.socialify_android.ui.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Preview(showBackground = false)
@Composable
fun ChatListElement() {

    Surface {
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
                    .size(86.dp)
                    .clip(CircleShape)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "Nazwa",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    "Od kogo: Ostatnio wysłana wia... · 21:15",
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }
        }
    }
}
