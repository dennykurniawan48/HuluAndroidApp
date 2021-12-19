package com.signaltekno.huluapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.signaltekno.huluapp.screen.HomeScreen
import com.signaltekno.huluapp.screen.OnboardScreen
import com.signaltekno.huluapp.screen.SplashScreen
import com.signaltekno.huluapp.viewmodel.SharedViewModel

@ExperimentalPagerApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    setBottomBar: (Boolean) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.SPLASH.route) {
        composable(Screen.SPLASH.route) {
            SplashScreen(navController = navController, viewModel = sharedViewModel, setBottomBar)
        }
        composable(Screen.ONBOARD.route) {
            OnboardScreen(navController = navController, viewModel = sharedViewModel, setBottomBar)
        }
        composable(Screen.HOME.route) {
            HomeScreen(navController = navController, sharedViewModel = sharedViewModel, setBottomBar)
        }
    }
}