package nl.ng.projectcargohubapp

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.DelicateCoroutinesApi
import nl.ng.projectcargohubapp.navigation.Screen
import nl.ng.projectcargohubapp.ui.theme.Shapes

@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
    "UnrememberedMutableState"
)
@Composable
fun UnloadingScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    viewModel.getTrips()
    val tripState by viewModel.list.collectAsState()

    viewModel.getOrders()
    val orderState by viewModel.orderList.collectAsState()

    var selected by remember { mutableStateOf(false) }
    val color = if (selected) Color(0xFF31C52E) else Color(0xFFE7A33E)

    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Unload orders - Trip",
                        color = Color.White
                    )
                },
                backgroundColor = Color(0xFF00083CD)
            )
        },
        content = {
            Column() {
                for (i: Int in 1..tripState.size) {
                    if (tripState[i - 1].status == "ACTIVE") {
                        var expandedState by remember { mutableStateOf(false) }
                        val rotationState by animateFloatAsState(
                            targetValue = if (expandedState) 180f else 0f
                        )

                        for (o: Int in orderState.indices) {
                            if (tripState[i - 1].bookingID == orderState[o].bookingID) {

                                viewModel.getAirwayBills(orderState[o].orderID)
                                val airwayBillsState by viewModel.airwayBillsList.collectAsState()

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
                                    onClick = { expandedState = !expandedState },
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
                                                text = "Order nr: ${orderState[o].orderID}",
                                                fontSize = MaterialTheme.typography.h6.fontSize,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            IconButton(
                                                modifier = Modifier
                                                    .alpha(ContentAlpha.medium)
                                                    .weight(1f),
                                                onClick = { expandedState = !expandedState }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ArrowDropDown,
                                                    contentDescription = "Drop-Down Arrow"
                                                )
                                            }
                                        }
                                        if (expandedState) {
                                            for (a: Int in airwayBillsState.indices) {
                                                if (airwayBillsState[a].orderID == orderState[o].orderID) {
                                                    Text(text = " AirwayBill ID: ${airwayBillsState[a].airwayBillID}")
                                                    Text(text = " Pieces: ${airwayBillsState[a].pcs}")
                                                    Text(text = " Weight: ${airwayBillsState[a].weight}")
                                                    Text(text = " Volume: ${airwayBillsState[a].volume}")
                                                    Text(text = " Height: ${airwayBillsState[a].height}")
                                                }
                                            }
                                        }
                                        Button(
                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = color
                                            ),
                                            onClick = { selected = !selected },
                                            modifier = Modifier.width(200.dp)
                                        ) {
                                            Text(text = "Confirm order")
                                        }
                                    }
                                }
                            }
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = color
                            ),
                            onClick = {
                                viewModel.updateOrderStatus(orderState[i].orderID, "LOADED")
                                viewModel.updateTripStatus(tripState[i - 1].bookingID, "CLOSED")
                                navController.navigate(Screen.TripScreen.route)
                            },
                            enabled = selected,
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text(text = "Finish trip")
                        }
                    }
                }
            }
        }
    )
}