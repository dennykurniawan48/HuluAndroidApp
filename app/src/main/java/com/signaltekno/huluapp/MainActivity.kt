package com.signaltekno.huluapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.signaltekno.huluapp.component.CustomBottomBar
import com.signaltekno.huluapp.model.BottomNavItem
import com.signaltekno.huluapp.navigation.Screen
import com.signaltekno.huluapp.navigation.SetupNavigation
import com.signaltekno.huluapp.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SharedViewModel by viewModels()
            val navController = rememberNavController()
            var bottomState by rememberSaveable { mutableStateOf(false) }
            Scaffold(
                topBar = {
//                         TopAppBar(
//                             title = { Text(text = "Daftar prmintaan") },
//                             navigationIcon = {
//                                 IconButton(onClick = { navController.popBackStack() }) {
//                                     Icon(Icons.Filled.ArrowBack,"Back")
//                                 }
//                             }
//
//                         )
                },
                content={
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = it.calculateBottomPadding())) {
                        SetupNavigation(navController = navController, viewModel, setBottomBar = {
                            bottomState = it
                        })
                    }
                },
                bottomBar = {
                    if(bottomState) {
                        CustomBottomBar(items = listOf(
                            BottomNavItem(
                                name = "Home",
                                route = Screen.HOME.route,
                                Icons.Filled.Home
                            ),
                            BottomNavItem(
                                name = "Search",
                                route = Screen.SEARCH.route,
                                Icons.Filled.Search
                            ),
                            BottomNavItem(
                                name = "Fav",
                                route = Screen.FAVOURITE.route,
                                Icons.Filled.Favorite
                            )
                        ),
                            navController = navController,
                            onItemClick = { navController.navigate(it.route) })
                    }
                }
            )
           
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    return navController.currentBackStackEntry?.destination?.route
}

