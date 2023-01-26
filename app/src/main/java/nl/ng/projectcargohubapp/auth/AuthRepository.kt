package nl.ng.projectcargohubapp.auth

import nl.ng.projectcargohubapp.model.AirwayBillsItems
import nl.ng.projectcargohubapp.model.Items
import nl.ng.projectcargohubapp.model.OrderItems

interface AuthRepository {
    suspend fun signUp(email: String, password: String): AuthResult<String>
    suspend fun signIn(email: String, password: String): AuthResult<String>
    suspend fun getTrips(DriverID: String, date: String): List<Items>
    suspend fun getOrders(): List<OrderItems>
    suspend fun getAirwayBills(orderID: String): List<AirwayBillsItems>
    suspend fun updateOrderStatus(orderID: String, status: String)
    suspend fun updateTripStatus(BookingID: String, Status: String)
    suspend fun registerDriver(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        companyID: String
    )
    suspend fun authenticate(): AuthResult<Unit>
}