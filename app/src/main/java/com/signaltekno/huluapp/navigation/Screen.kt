package com.signaltekno.huluapp.navigation

sealed class Screen(val route: String){
    object ONBOARD: Screen("ONBOARD")
    object SPLASH: Screen("SPLASH")
    object HOME: Screen("HOME")
    object SEARCH: Screen("SEARCH")
    object FAVOURITE: Screen("FAVOURITE")
}
