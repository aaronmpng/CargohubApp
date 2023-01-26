package nl.ng.projectcargohubapp.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_Screen")
    object TripScreen : Screen("trip_Screen")
    object ActiveTripScreen : Screen("activeTrips_Screen")
    object LoadingScreen : Screen("loading_Screen")
    object ArrivedTripScreen : Screen("arrivedTrip_Screen")
    object UnloadingScreen : Screen("unloading_Screen")
    object RegisterScreen : Screen("register_Screen")
    object ClosedTripScreen : Screen("closedTrip_Screen")
}
