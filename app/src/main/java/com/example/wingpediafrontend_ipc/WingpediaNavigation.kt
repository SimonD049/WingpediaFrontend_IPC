package com.example.wingpediafrontend_ipc

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*

class WingpediaNavigation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("wingpedia_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        setContent {
            val navController = rememberNavController()

            val startDestination = if (isLoggedIn) "Home_screen" else "Welcome_screen"

            NavHost(navController = navController, startDestination = startDestination, builder = {
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
                    NavigationBar(navController)
                }
            })
        }
    }
}