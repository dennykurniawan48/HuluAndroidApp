package com.signaltekno.huluapp.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.signaltekno.huluapp.screen.*
import com.signaltekno.huluapp.viewmodel.SharedViewModel

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
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
        composable(Screen.DETAIL.route) {
            //setBottomBar(false)
            DetailScreen(
                navController = navController,
                setBottomBar = setBottomBar,
                sharedViewModel = sharedViewModel
            )
        }
        composable(Screen.SEARCH.route){
//            sharedViewModel.setSearchIdle()
            SearchScreen(sharedViewModel = sharedViewModel, navController = navController, setBottomBar = setBottomBar)
        }
    }
}