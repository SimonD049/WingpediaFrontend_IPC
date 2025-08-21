package com.example.wingpediafrontend_ipc

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wingpediafrontend_ipc.WingpediaDatabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        navController.navigate("Home_screen") {
            popUpTo("Home_screen") { inclusive = false }
            launchSingleTop = true
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { },
            modifier = Modifier
                .width(320.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFFA7A7A7))
        ) {
            Text(
                text = "Edit Account",
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { },
            modifier = Modifier
                .width(320.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFFA7A7A7))
        ) {
            Text(
                text = "App Settings",
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            coroutineScope.launch {
                supabase.auth.signOut()

                val sharedPref = navController.context.getSharedPreferences("wingpedia_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("is_logged_in", false)
                    apply()
                }

                navController.navigate("LoginSignup_screen") {
                    popUpTo("Home_screen") { inclusive = true }
                }
            }
        },
            modifier = Modifier
                .width(320.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFFE24343))
        ) {
            Text(
                text = "Log out",
                fontSize = 25.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen(navController = rememberNavController())
    }
}
