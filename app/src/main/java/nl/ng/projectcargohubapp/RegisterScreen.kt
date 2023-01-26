package nl.ng.projectcargohubapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val firstname = remember { mutableStateOf("") }
    val lastname = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val companyId = remember { mutableStateOf("b6de98ae-29dc-4a0f-a188-39890e315b7b") }

    Column() {
        Surface(
            modifier = Modifier
                .fillMaxSize(), color = Color(0xFF00083CD)
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
                    value = email.value, onValueChange = { email.value = it },
                    label = {
                        Text(
                            text = "Type in email",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(text = "Password")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                TextField(
                    value = password.value, onValueChange = { password.value = it },
                    label = {
                        Text(
                            text = "Type in password",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(text = "firstname")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                TextField(
                    value = firstname.value, onValueChange = { firstname.value = it },
                    label = {
                        Text(
                            text = "Type in firstname",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(text = "lastname")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                TextField(
                    value = lastname.value, onValueChange = { lastname.value = it },
                    label = {
                        Text(
                            text = "Type in lastname",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(text = "phone number")
                Spacer(modifier = Modifier.padding(top = 5.dp))
                TextField(
                    value = phoneNumber.value, onValueChange = { phoneNumber.value = it },
                    label = {
                        Text(
                            text = "Type in phone number",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF002197)
                    ),
                    onClick = {
                        viewModel.RegisterDriver(
                            email.value,
                            password.value,
                            firstname.value,
                            lastname.value,
                            phoneNumber.value,
                            companyId.value
                        )
                    }) {
                    Text(
                        text = "Login",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                //ClickableText(text = AnnotatedString("Forgot password"), onClick = {navController.navigate(Screen.ForgotPasswordPage.route)})
            }
        }
        /*Surface(
            modifier = Modifier
                .fillMaxSize(), color = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Image"
            )
        }//

         */
    }
}

