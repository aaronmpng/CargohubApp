package nl.ng.projectcargohubapp

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.ng.projectcargohubapp.navigation.Screen
import nl.ng.projectcargohubapp.ui.theme.Shapes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActiveTripScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    viewModel.getTrips()
    val state by viewModel.list.collectAsState()

    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Active trips",
                        color = Color.White
                    )
                },
                backgroundColor = Color(0xFF00083CD)
            )
        },
        content = {
            Column() {
                Row(

                ) {
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFD1D1D1)
                    ), onClick = { navController.navigate(Screen.TripScreen.route) }) {
                        Text(text = "Planned Trips")
                    }
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF0083CD)
                    ),
                        onClick = {}) {
                        Text(text = "Active trips")
                    }
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFD1D1D1)
                    ),
                        onClick = { navController.navigate(Screen.ClosedTripScreen.route) }) {
                        Text(text = "Closed Trips")
                    }
                }

                for (i: Int in 1..state.size) {

                    if (state[i - 1].status == "ACTIVE") {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = LinearOutSlowInEasing
                                    )
                                ),
                            shape = Shapes.medium,
                            onClick = {},
                            backgroundColor = Color(0xFFD1D1D1)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Row {
                                    Text(
                                        modifier = Modifier
                                            .weight(6f),
                                        text = "$i ${state[i - 1].airportPickup} -> ${state[i - 1].airportDrop}",
                                        fontSize = MaterialTheme.typography.h6.fontSize,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    IconButton(
                                        modifier = Modifier
                                            .alpha(ContentAlpha.medium)
                                            .weight(1f),
                                        onClick = {}
                                    ) {}
                                }
                                Column {
                                    Text("Pick up time: ${state[i - 1].startDateTime}")
                                    Text("Handler: ${state[i - 1].pickupHandler}")
                                }

                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFFFF4F4F)
                                    ),
                                    onClick = {
                                        navController.navigate(Screen.TripScreen.route)
                                        viewModel.updateTripStatus(
                                            state[i - 1].bookingID,
                                            "PLANNED"
                                        )
                                    },
                                    modifier = Modifier.width(200.dp)
                                ) {
                                    Text(text = "Cancel")
                                }
                            }
                        }
                    }
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFE7A33E)
                    ),
                    onClick = { navController.navigate(Screen.LoadingScreen.route) },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(text = "Activate trip")
                }
            }
        }
    )
}