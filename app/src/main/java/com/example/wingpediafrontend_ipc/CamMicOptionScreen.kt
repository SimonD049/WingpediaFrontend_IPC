package com.example.wingpediafrontend_ipc

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CamMicOptionScreen (navController: NavController, modifier: Modifier = Modifier) {

    BackHandler {
        navController.popBackStack() // goes back to the previous screen in stack
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Identify a bird using...",
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF2E7D32))
        ) {
            Text(
                text = "IMAGE",
                fontSize = 35.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "-- OR --",
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF2E7D32))
        ) {
            Text(
                text = "AUDIO",
                fontSize = 35.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CamMicOptionScreenPreview () {
    CamMicOptionScreen(navController = rememberNavController())
}