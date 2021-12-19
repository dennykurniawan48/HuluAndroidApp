package com.signaltekno.huluapp.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.signaltekno.huluapp.viewmodel.SharedViewModel

@Composable
fun DetailMovie(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    setBottomBar: (Boolean) -> Unit,
    dataDetail: String
) {
    LaunchedEffect(key1 = true){
        setBottomBar(false)
    }

}