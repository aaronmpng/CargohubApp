package nl.ng.projectcargohubapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.ng.projectcargohubapp.navigation.Screen
import nl.ng.projectcargohubapp.ui.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "ProduceStateDoesNotAssignValue",
    "StateFlowValueCalledInComposition"
)

@Composable
fun ClosedTripScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    val token = MainViewModel.data
    Log.d("tokenData", token)
    viewModel.getTrips()
    val state by viewModel.list.collectAsState()

    viewModel.getOrders()
    val orderState by viewModel.orderList.collectAsState()

    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Trips today",
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
                    ), onClick = {navController.navigate(Screen.TripScreen.route) }) {
                        Text(text = "Planned Trips")
                    }

                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFD1D1D1)
                    ),
                        onClick = { navController.navigate(Screen.ActiveTripScreen.route) }) {
                        Text(text = "Active trips")
                    }
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF0083CD)
                    ),
                        onClick = { }) {
                        Text(text = "Closed Trips")
                    }
                }

                for (i: Int in 1..state.size) {

                    var expandedState by remember { mutableStateOf(false) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f
                    )
                    if (state[i - 1].status == "CLOSED") {
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
                            onClick = {
                                expandedState = !expandedState
                            },
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
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    IconButton(
                                        modifier = Modifier
                                            .alpha(ContentAlpha.medium)
                                            .weight(1f)
                                            .rotate(rotationState),
                                        onClick = {
                                            expandedState = !expandedState
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Drop-Down Arrow"
                                        )
                                    }
                                }
                                Column{
                                    Text("Handler: ${state[i-1].startDateTime}")
                                    Text("Handler: ${state[i-1].pickupHandler}")
                                }
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                                if (expandedState) {
                                    Text(
                                        modifier = Modifier,
                                        text = "Orders",
                                        fontWeight = FontWeight.Bold
                                    )
                                    for (o: Int in orderState.indices) {
                                        if (state[i - 1].bookingID == orderState[o].bookingID) {
                                            Text(text = " OrderID: ${orderState[o].orderID}")
                                            Text(text = " Pickup time: ${orderState[o].pickupTime}")
                                            Text(text = " Time slot: ${orderState[o].timeSlot}")
                                            Spacer(modifier = Modifier.padding(top = 8.dp))
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 5.dp))
                    }
                }
            }
        }
    )
}

