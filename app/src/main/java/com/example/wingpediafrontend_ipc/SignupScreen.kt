package com.example.wingpediafrontend_ipc

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wingpediafrontend_ipc.WingpediaDatabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

//for signup use: email, username, password
// of course add validation for all stuff
//also have confirm password
//add password visibility eye thingy
//try to log in if existing account is signed in or just say account already exists idk

@Composable
fun SignupScreen(navController: NavController){

    // For error messages during validation
    var UsernameError by remember { mutableStateOf("") }
    var EmailError by remember { mutableStateOf("") }
    var PassError by remember { mutableStateOf("") }
    var PassConError by remember { mutableStateOf("") }

    // Regular expression for password input: Must be at least 8-characters long, a digit, and a special character. (letter casing doesn't matter)
    val PassRegex = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*()_+])[A-Za-z\\d!@#\$%^&*()_+]{8,}$")

    // For database -  Supabase
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Sign In",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Create a new account",
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(30.dp))

        // USERNAME
        Box(
            modifier = Modifier
                .height(28.dp)
                .width(270.dp)
        ) {
            Text(
                text = "Username:",
                fontSize = 23.sp,
            )
        }

        // For username input
        var usernameField_Signin by remember {
            mutableStateOf("")
        }

        BasicTextField( // Username input field
            value = usernameField_Signin,
            onValueChange = { usernameField_Signin = it },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp)
                .padding(10.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                .padding(3.dp),
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

        Box( // For username error message
            modifier = Modifier
                .height(28.dp)
                .width(270.dp)
        ) {
            Text(
                text = UsernameError,
                fontSize = 15.sp,
                color =  Color.Red
            )
        }


        //EMAIL ADDRESS
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

        // For email input
        var emailField_Signin by remember {
            mutableStateOf("")
        }

        BasicTextField( // Email input field
            value = emailField_Signin,
            onValueChange = { emailField_Signin = it },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp)
                .padding(10.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                .padding(3.dp),
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

        Box( // For email error message
            modifier = Modifier
                .height(28.dp)
                .width(270.dp)
        ) {
            Text(
                text = EmailError,
                fontSize = 15.sp,
                color =  Color.Red
            )
        }

        //PASSWORD
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

        // For password input with reveal button
        val state = remember { TextFieldState() }
        var showPassword by remember { mutableStateOf(false) }
        BasicSecureTextField( // Password input field
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
                .padding(3.dp),
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

        Box( // For password  error message
            modifier = Modifier
                .width(270.dp)
        ) {
            Text(
                text = PassError,
                fontSize = 15.sp,
                softWrap = true,
                color =  Color.Red
            )
        }

        //CONFIRM PASSWORD
        Box(
            modifier = Modifier
                .height(28.dp)
                .width(270.dp)
        ) {
            Text(
                text = "Confirm Password:",
                fontSize = 23.sp,
            )
        }

        // Confirm password input with reveal button
        val con_state = remember { TextFieldState() }
        var con_showPassword by remember { mutableStateOf(false) }
        BasicSecureTextField( // Confirm password input field
            state = con_state,
            textObfuscationMode =
                if (con_showPassword) {
                    TextObfuscationMode.Visible
                } else {
                    TextObfuscationMode.RevealLastTyped
                },
            modifier = Modifier
                .width(300.dp)
                .padding(10.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                .padding(3.dp),
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
                        if (con_showPassword) {
                            Icons.Filled.Visibility
                        } else
                        {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Toggle Password visibility",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .requiredSize(48.dp) .padding(16.dp)
                            .clickable { con_showPassword = !con_showPassword }
                    )
                }
            },
            textStyle = TextStyle(
                fontSize = 20.sp
            )
        )

        Box( // For confirm password  error message
            modifier = Modifier
                .height(28.dp)
                .width(270.dp)
        ) {
            Text(
                text = PassConError,
                fontSize = 15.sp,
                color =  Color.Red
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        //CONFIRM SIGN IN BUTTON
        Button(onClick = {

            // Turn password values into text for validation
            val passValidate = state.text.toString()
            val conpassValidate = con_state.text.toString()

            // Validation requirements
            UsernameError = if(usernameField_Signin.isBlank()) "Please enter a valid username" else ""
            EmailError = if(emailField_Signin.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailField_Signin).matches()) "Please enter a valid Email address" else ""
            PassError = if(passValidate.isBlank() || !PassRegex.matches(passValidate)) "Password must be 8 characters long & contain at least a number and special character" else ""
            PassConError = if(conpassValidate.isBlank() || passValidate != conpassValidate) "Password does not match" else ""

            // If no errors present, create account
            if (UsernameError.isEmpty() && EmailError.isEmpty() && PassError.isEmpty() && PassConError.isEmpty()) {
                coroutineScope.launch {
                    try {

                        val signUpResult = supabase.auth.signUpWith(Email) {
                            email = emailField_Signin
                            password = passValidate
                        }

                        val userId = supabase.auth.currentUserOrNull()?.id
                            ?: throw Exception("Sign up failed: No user returned")

                        supabase.from("User Credentials").insert(
                            mapOf(
                                "id" to userId.toString(),
                                "username" to usernameField_Signin,
                                "email" to emailField_Signin
                            )
                        )

                        navController.navigate("Home_screen") {
                            popUpTo("signup") { inclusive = true }
                        }

                    } catch (e: Exception) {
                        val errorMessage = e.message ?: "Something went wrong"

                        // Toast message: checks if username is already taken
                        if (errorMessage.contains("duplicate key value", ignoreCase = true) &&
                            errorMessage.contains("username", ignoreCase = true)) {
                            Toast.makeText(context, "Username is already taken", Toast.LENGTH_LONG).show()
                        // Toast message: checks if email is already registered
                        } else if (errorMessage.contains("already registered", ignoreCase = true) ||
                            errorMessage.contains("User already registered", ignoreCase = true)) {
                            Toast.makeText(context, "Email Address is already registered", Toast.LENGTH_LONG).show()
                        // General Toast error message
                        } else {
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        },
            modifier = Modifier
                .width(250.dp)
                .height(70.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF2E7D32))
        ) {
            Text(
                text = "Create Account",
                fontSize = 28.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SigninScreenPreview() {
    MaterialTheme {
        SignupScreen(navController = rememberNavController())
    }
}