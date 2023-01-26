package nl.ng.projectcargohubapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nl.ng.projectcargohubapp.auth.AuthRepository
import nl.ng.projectcargohubapp.auth.AuthResult
import nl.ng.projectcargohubapp.model.AirwayBillsItems
import nl.ng.projectcargohubapp.model.Items
import nl.ng.projectcargohubapp.model.OrderItems
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(AuthState())

    var list = MutableStateFlow(emptyList<Items>())
    val listState: StateFlow<List<Items>>
        get() = list

    var orderList = MutableStateFlow(emptyList<OrderItems>())
    val orderListState: StateFlow<List<OrderItems>>
        get() = orderList

    var airwayBillsList = MutableStateFlow(emptyList<AirwayBillsItems>())
    val airwayBillsListState: StateFlow<List<AirwayBillsItems>>
        get() = airwayBillsList

    private val resultChannel = Channel<AuthResult<String>>()
    val authResults = resultChannel.receiveAsFlow()

    companion object {
        var data: String = ""
    }

    init {
        //authenticate()
    }

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignInEmailChanged -> {
                state = state.copy(signInEmail = event.value)
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthUiEvent.SignIn -> {
                signIn()
            }
            is AuthUiEvent.SignUpEmailChanged -> {
                state = state.copy(signUpEmail = event.value)
            }
            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }
            is AuthUiEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            val result = repository.signUp(
                email = state.signUpEmail,
                password = state.signUpPassword
            )
            resultChannel.send(result)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            val result = repository.signIn(
                email = state.signInEmail,
                password = state.signInPassword
            )
            resultChannel.send(result)
            data = result.data.toString()
        }
    }

    fun getTrips() {
        viewModelScope.launch {
            val token: String = data
            val jwt: JWT = JWT(token)
            val tokenClaim: String? =
                jwt.getClaim("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/sid").asString()
            Log.d("claim", tokenClaim.toString())

            val result = repository.getTrips(
                DriverID = tokenClaim.toString(),
                date = "2023-01-25"
            )
            list.value = result
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            val result = repository.getOrders()
            orderList.value = result
        }
    }

    fun getAirwayBills(orderID: String) {
        viewModelScope.launch {
            val result = repository.getAirwayBills(
                orderID = orderID
            )
            airwayBillsList.value = result
        }
    }

    fun updateOrderStatus(orderID: String, status: String) {
        viewModelScope.launch {
            val result = repository.updateOrderStatus(
                orderID = orderID,
                status = status
            )
        }
    }

    fun updateTripStatus(bookingId: String, status: String) {
        viewModelScope.launch {
            val result = repository.updateTripStatus(
                BookingID = bookingId,
                Status = status
            )
        }
    }

    fun RegisterDriver(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        companyID: String
    ) {
        viewModelScope.launch {
            val result = repository.registerDriver(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                companyID = companyID
            )
        }
    }

    /*private fun authenticate() {
         viewModelScope.launch {
             val result = repository.authenticate()
             resultChannel.send(result)
         }
     }
     */
}

