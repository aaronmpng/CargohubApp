package nl.ng.projectcargohubapp.model

data class TripItems(
    val value : List<Items>
)

data class Items(
    var bookingID : String,
    var driverID : String,
    var vehicleID : String,
    var pickupHandler : String,
    var pickUpAddress : String,
    var pickUpAddressLatLong : String,
    var airportPickup : String,
    var dropOffHandler : String,
    var airportDrop : String,
    var unloadingAddress : String,
    var unloadingAddressLatLong : String,
    var status : String,
    var startDateTime : String,
    var distanceKM : String,
    var estimatedArrivalTime : String,
    var currentLocation : String,
)
