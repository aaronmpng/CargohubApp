package nl.ng.projectcargohubapp.model

data class TripOrders(
    var value: List<OrderItems>
)

data class OrderItems(
    var orderID: String,
    var bookingID: String,
    var vehicleID: String,
    var mrnNumber: String,
    var pickupDate: String,
    var pickupTime: String,
    var timeSlot: String,
    var status: String,
    var companyID: String,
    var image: String,
)