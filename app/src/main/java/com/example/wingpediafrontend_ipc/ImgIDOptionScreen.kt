package com.example.wingpediafrontend_ipc

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImgIDOptionScreen () {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose Image Identification Option",
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(180.dp)
                .height(180.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF2E7D32))
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.camera_option),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "CAMERA",
                    fontSize = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(180.dp)
                .height(180.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF2E7D32))
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.gallery_option),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "GALLERY",
                    fontSize = 17.sp
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ImgIDOptionScreenPreview () {
    ImgIDOptionScreen()
}