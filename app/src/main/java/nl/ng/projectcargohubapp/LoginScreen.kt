package nl.ng.projectcargohubapp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.ng.projectcargohubapp.auth.AuthResult
import nl.ng.projectcargohubapp.navigation.Screen

@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    navController.navigate(Screen.TripScreen.route)
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "You're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Column() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(430.dp), color = Color(0xFF00083CD)
        ) {
            Column(modifier = Modifier.padding(40.dp)) {

                Text(
                    text = "Login",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(
                    text = "Email"
                )
                Spacer(modifier = Modifier.padding(top = 5.dp))
                TextField(
                    value = state.signInEmail,
                    onValueChange = { viewModel.onEvent(AuthUiEvent.SignInEmailChanged(it)) },
                    label = {
                        Text(
                            text = "Type in Email",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(text = "Password")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                TextField(
                    value = state.signInPassword,
                    onValueChange = { viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it)) },
                    label = {
                        Text(
                            text = "Type in password",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                )
                Spacer(modifier = Modifier.padding(top = 20.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF002197)
                    ), onClick = { viewModel.onEvent(AuthUiEvent.SignIn) }) {
                    Text(
                        text = "Login",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                // ClickableText(text = AnnotatedString("Register"), onClick = {navController.navigate(Screen.RegisterScreen.route)})
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize(), color = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Image"
            )
        }
    }
}

