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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.ng.projectcargohubapp.navigation.Screen
import nl.ng.projectcargohubapp.ui.theme.Shapes

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
    "InvalidColorHexValue"
)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArrivedTripScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    viewModel.getTrips()
    val tripState by viewModel.list.collectAsState()
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Arrived - Trip",
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
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFD1D1D1)
                        ), onClick = { },
                        enabled = false
                    ) {
                        Text(text = "Planned Trips")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFD1D1D1)
                        ),
                        onClick = { },
                        enabled = false
                    ) {
                        Text(text = "Active trips")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFD1D1D1)
                        ),
                        onClick = { },
                        enabled = false
                    ) {
                        Text(text = "Closed trips")
                    }
                }

                for (i: Int in 1..tripState.size) {

                    if (tripState[i - 1].status == "ACTIVE") {
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
                                        text = "$i ${tripState[i - 1].airportPickup} -> ${tripState[i - 1].airportDrop}",
                                        fontWeight = FontWeight.Bold,
                                    )
                                    IconButton(
                                        modifier = Modifier
                                            .alpha(ContentAlpha.medium)
                                            .weight(1f),
                                        onClick = {}
                                    ) {}
                                }
                                Column {
                                    Text("Drop off time: ${tripState[i - 1].estimatedArrivalTime}")
                                    Text("Handler: ${tripState[i - 1].dropOffHandler}")
                                }
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF31C52E)
                                    ),
                                    onClick = {
                                        navController.navigate(Screen.UnloadingScreen.route)
                                    },
                                    modifier = Modifier.width(200.dp)
                                ) {
                                    Text(text = "Arrived At Destination")
                                }
                            }
                        }
                    }
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFE7A33E)
                    ),
                    onClick = {},
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(text = "Pause")
                }
            }
        }
    )
}