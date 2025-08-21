package com.example.wingpediafrontend_ipc

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wingpediafrontend_ipc.WingpediaDatabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.providers.builtin.Email

@Composable
fun LoginScreen(navController: NavController){

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var LoginError by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log In",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Log in to your account",
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(90.dp))

        Box(
            modifier = Modifier
                .height(28.dp)
                .width(270.dp)
        ) {
            Text(
                text = "Email Address:",
                fontSize = 23.sp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        var loginField_Email by remember {
            mutableStateOf("")
        }

        BasicTextField(
            value = loginField_Email,
            onValueChange = { loginField_Email = it },
            modifier = Modifier
                .width(300.dp)
                .height(90.dp)
                .padding(10.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                .padding(10.dp),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        innerTextField()
                    }
                }
            },
            textStyle = TextStyle(
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .height(28.dp)
                .width(270.dp)
        ) {
            Text(
                text = "Password:",
                fontSize = 23.sp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        var state = remember { TextFieldState() }
        var showPassword by remember { mutableStateOf(false) }
        BasicSecureTextField(
            state = state,
            textObfuscationMode =
                if (showPassword) {
                    TextObfuscationMode.Visible
                } else {
                    TextObfuscationMode.RevealLastTyped
                },
            modifier = Modifier
                .width(300.dp)
                .padding(10.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                .padding(10.dp),
            decorator = {innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        innerTextField()
                    }
                    Icon(
                        if (showPassword) {
                            Icons.Filled.Visibility
                        } else
                        {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Toggle Password visibility",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .requiredSize(48.dp) .padding(16.dp)
                            .clickable { showPassword = !showPassword }
                    )
                }
            },
            textStyle = TextStyle(
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box( // For input fields error message
            modifier = Modifier
                .width(270.dp)
        ) {
            Text(
                text = LoginError,
                fontSize = 15.sp,
                softWrap = true,
                textAlign = TextAlign.Center,
                color =  Color.Red
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {

            val loginPass = state.text.toString()
            // Error message for empty login fields
            LoginError = if(loginField_Email.isBlank() || loginPass.isBlank()) "Please enter a valid email address and password" else ""

            if (LoginError.isEmpty()) {
                coroutineScope.launch {
                    try {
                        supabase.auth.signInWith(Email) {
                            email = loginField_Email
                            this.password = loginPass
                        }

                        val sharedPref = context.getSharedPreferences("wingpedia_prefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putBoolean("is_logged_in", true)
                            apply()
                        }

                        navController.navigate("Home_screen") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }

                    } catch (e: Exception) {
                        if (e.message?.contains("Invalid login credentials") == true) {
                            // Error message if entered input is incorrect
                            LoginError = "Email address or password entered is incorrect"
                        } else {
                            // Error message for general errors
                            Toast.makeText(context, "An error occurred, please try again", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        },
            modifier = Modifier
                .width(200.dp)
                .height(70.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF2E7D32))
        ) {
            Text(
                text = "Log In",
                fontSize = 28.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(navController = rememberNavController())
    }
}

//for login only use email and password