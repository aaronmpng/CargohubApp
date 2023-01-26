package nl.ng.projectcargohubapp.auth

import android.content.SharedPreferences
import android.util.Log
import nl.ng.projectcargohubapp.model.AirwayBillsItems
import nl.ng.projectcargohubapp.model.Items
import nl.ng.projectcargohubapp.model.OrderItems
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository {
    override suspend fun signUp(email: String, password: String): AuthResult<String> {

        return try {
            api.signUp(
                request = AuthRequest(
                    email = email,
                    password = password
                )
            )
            signIn(email, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<String> {

        return try {
            val response = api.signIn(
                request = AuthRequest(
                    email = email,
                    password = password
                )
            )
            Log.d("token", response.accessToken)

            prefs.edit()
                .putString("jwt", response.accessToken)
                .apply()

            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()

            Log.d("token", token)
            AuthResult.Authorized(token)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun registerDriver(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        companyID: String
    ) {
        try {
            val token = prefs.getString("jwt", null)
            val response = api.registerUser(
                register = RegisterUser(
                    email = email,
                    password = password,
                    firstname = firstName,
                    lastname = lastName,
                    phoneNumber = phoneNumber,
                    companyId = companyID,
                ),
                accessToken = " Bearer ".plus(token)
            )

        } catch (e: HttpException) {
            if (e.code() == 401) {
                e.message()
            }
        } catch (e: Exception) {
            e.message
        }
    }

    override suspend fun getTrips(DriverID: String, date: String): List<Items> {
        var list: List<Items> = listOf()

        try {
            val token = prefs.getString("jwt", null)
            Log.d("test", "Bearer ".plus(token))

            val response = api.getTrips(
                accessToken = " Bearer ".plus(token),
                DriverID = DriverID,
                date = date
            )
            list = response

        } catch (e: HttpException) {
            if (e.code() == 401) {
                e.message()
            }
        } catch (e: Exception) {
            e.message
        }
        return list
    }

    override suspend fun getAirwayBills(orderID: String): List<AirwayBillsItems> {
        var list: List<AirwayBillsItems> = listOf()

        try {
            val token = prefs.getString("jwt", null)
            Log.d("test", "Bearer ".plus(token))

            val response = api.getAirwayBills(
                orderID = orderID,
                accessToken = " Bearer ".plus(token)
            )
            list = response

        } catch (e: HttpException) {
            if (e.code() == 401) {
                e.message
            }
        } catch (e: Exception) {
            e.message
        }
        return list
    }

    override suspend fun getOrders(): List<OrderItems> {
        var orderlist: List<OrderItems> = listOf()
        try {
            val token = prefs.getString("jwt", null)
            val response = api.getOrders(
                accessToken = "Bearer ".plus(token)
            )
            orderlist = response
            Log.d("order", "1")
        } catch (e: HttpException) {
            if (e.code() == 401) {
                e.message
            }
        } catch (e: Exception) {
            e.message
        }
        return orderlist
    }

    override suspend fun updateOrderStatus(orderID: String, status: String) {
        try {
            val token = prefs.getString("jwt", null)
            val response = api.updateStatus(
                orderID = orderID,
                status = status,
                accessToken = "Bearer ".plus(token)
            )
        } catch (e: HttpException) {
            if (e.code() == 401) {
                e.message
            }
        } catch (e: Exception) {
            e.message
        }
    }

    override suspend fun updateTripStatus(BookingID: String, Status: String) {
        try {
            val token = prefs.getString("jwt", null)
            val response = api.updateTripStatus(
                bookingID = BookingID,
                status = Status,
                accessToken = "Bearer ".plus(token)
            )
        } catch (e: HttpException) {
            if (e.code() == 401) {
                e.message
            }
        } catch (e: Exception) {
            e.message
        }
    }


    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate("bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}
