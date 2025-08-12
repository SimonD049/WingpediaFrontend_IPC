package com.example.wingpediafrontend_ipc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun IntroScreen(navController: NavController) { // Intro dialog with 3 sequences
    val IntroTexts = listOf(
        "Discover Birds Easily" to "Identify birds using AI-powered image and sound recognition.",
        "Track Your Sightings" to "Save and track birds you’ve spotted in your area.",
        "Learn Bird Facts" to "Access detailed info and fun facts about each species."
    )

    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Image( // Image for Introduction
            painter = painterResource(id = R.drawable.intro_screen_graphic),
            contentDescription = "Welcome Image",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text( // Primary Text for Intro
            text = IntroTexts[currentIndex].first,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text( // Secondary Text for Intro
            text = IntroTexts[currentIndex].second,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp)) // Space before arrows


        Row( // Row for arrow buttons
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton( // Left Arrow Button
                onClick = { if (currentIndex > 0) currentIndex-- },
                enabled = currentIndex > 0
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leftarrow),
                    contentDescription = "Previous",
                    modifier = Modifier.size(64.dp)
                )
            }

            IconButton( // Right Arrow Button
                onClick = {
                    if (currentIndex < IntroTexts.lastIndex - 1) {
                        currentIndex++
                    } else {
                        navController.navigate("LoginSignup_screen")
                    }
                },
                enabled = currentIndex < IntroTexts.lastIndex
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rightarrow),
                    contentDescription = "Next",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    MaterialTheme {
        IntroScreen(navController = rememberNavController())
    }
}
