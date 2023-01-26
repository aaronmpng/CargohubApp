package nl.ng.projectcargohubapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import nl.ng.projectcargohubapp.*


@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.TripScreen.route) {
            TripScreen(navController = navController)
        }

        composable(route = Screen.ActiveTripScreen.route) {
            ActiveTripScreen(navController = navController)
        }

        composable(route = Screen.LoadingScreen.route) {
            LoadingScreen(navController = navController)
        }

        composable(route = Screen.ArrivedTripScreen.route) {
            ArrivedTripScreen(navController = navController)
        }

        composable(route = Screen.UnloadingScreen.route) {
            UnloadingScreen(navController = navController)
        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }

        composable(route = Screen.ClosedTripScreen.route) {
            ClosedTripScreen(navController = navController)
        }
    }
}

