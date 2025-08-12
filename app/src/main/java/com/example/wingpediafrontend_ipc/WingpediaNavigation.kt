package com.example.wingpediafrontend_ipc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*

class WingpediaNavigation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Welcome_screen", builder = {
                composable("Welcome_screen") {
                    WelcomeScreen(navController)
                }
                composable("Intro_screen") {
                    IntroScreen(navController)
                }
                composable("LoginSignup_screen") {
                    LoginSignupScreen(navController)
                }
                composable("Login_screen") {
                    LoginScreen(navController)
                }
                composable("Signup_screen") {
                    SignupScreen(navController)
                }
                composable("Home_screen") {
                    HomeScreen(navController)
                }
            })
        }
    }
}