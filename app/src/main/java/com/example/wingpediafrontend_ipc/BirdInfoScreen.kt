package com.example.wingpediafrontend_ipc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun BirdInfoScreen() {

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                modifier = Modifier.matchParentSize(),
                painter = rememberAsyncImagePainter(R.drawable.img_error),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Green)
        ) {
            Image(
                modifier = Modifier
                    .matchParentSize(),
                painter = rememberAsyncImagePainter(R.drawable.birdinfo_bg), // 👈 your background image resource
                contentDescription = null,
                contentScale = ContentScale.Crop, // fills the box proportionally
                alignment = Alignment.BottomCenter,         // 👈 Focus on bottom part of the image// makes the image fill the Box
            )

            // Optional: overlay content on top of the background
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Bird Info", color = Color.White)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirdInfoScreenPreview() {
    MaterialTheme {
        BirdInfoScreen()
    }
}