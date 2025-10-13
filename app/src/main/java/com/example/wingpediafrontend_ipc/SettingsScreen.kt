package com.example.wingpediafrontend_ipc

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.LaunchedEffect
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.example.wingpediafrontend_ipc.WingpediaDatabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    val id: String,
    val profile_pic: String?
)
@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()

    var ProfPic by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {

        //Retrieve user information from supabase
        val userID = supabase.auth.currentUserOrNull()?.id
        if (userID != null) {
            val profile = supabase.postgrest["User Credentials"]
                .select {
                    filter {
                        eq("id", userID)
                    }
                }
                .decodeSingle<User>()
            ProfPic = profile.profile_pic
        }
    }

    //For profile picture change
    val StoredPfp = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {

                val userID = supabase.auth.currentUserOrNull()?.id ?: return@launch
                val inputStream = navController.context.contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    val bytes = inputStream.readBytes()
                    inputStream.close()

                    val PfpFile = "$userID/${UUID.randomUUID()}.jpg"
                    supabase.storage.from("user_profilepics").upload(
                        PfpFile,
                        bytes
                    ) {
                        upsert = true
                    }

                    val PfpURL = supabase.storage.from("user_profilepics").publicUrl(PfpFile)

                    supabase.postgrest["User Credentials"].update({
                        set("profile_pic", PfpURL)
                    }) {
                        filter {
                            eq("id", userID)
                        }
                    }

                    val updatedProfile = supabase.postgrest["User Credentials"]
                        .select {
                            filter { eq("id", userID) }
                        }
                        .decodeSingle<User>()

                    ProfPic = updatedProfile.profile_pic

                } else {
                    Toast.makeText(navController.context, "An error occurred, please try again", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    BackHandler {
        navController.popBackStack() // goes back to the previous screen in stack
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = rememberAsyncImagePainter(ProfPic ?: R.drawable.default_profilepicture), //Drawable for default profile picture
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = { StoredPfp.launch("image/*") },
            modifier = Modifier
                .width(320.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFFA7A7A7))
        ) {
            Text(
                text = "Change Profile Picture",
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
                text = "Account Settings",
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
