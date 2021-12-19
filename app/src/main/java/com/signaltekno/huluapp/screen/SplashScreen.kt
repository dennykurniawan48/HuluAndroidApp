package com.signaltekno.huluapp.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.signaltekno.huluapp.R
import com.signaltekno.huluapp.navigation.Screen
import com.signaltekno.huluapp.ui.theme.OnBoardColor
import com.signaltekno.huluapp.ui.theme.Text700
import com.signaltekno.huluapp.viewmodel.SharedViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SharedViewModel,
    setBottomBar: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.OnBoardColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val onBoardFinish by viewModel.onboard.collectAsState(false)
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            setBottomBar(false)
        }
        Log.d("Slash", "set false")
        LaunchedEffect(key1 = onBoardFinish){
            delay(3000L)
            if(!onBoardFinish) {
                navController.navigate(Screen.ONBOARD.route) {
                    popUpTo(Screen.SPLASH.route) { inclusive = true }
                }
            }else{
                navController.navigate(Screen.HOME.route) {
                    popUpTo(Screen.SPLASH.route) { inclusive = true }
                }
//                context.startActivity(Intent(context, HomeActivity::class.java))
//                (context as Activity).finish()
            }
        }
        Image(painter = painterResource(id = R.drawable.logo1), contentDescription = "App Logo", modifier = Modifier.size(200.dp))
        Text(text = "Film Database", style = MaterialTheme.typography.h4, color = Text700, fontWeight = FontWeight.Bold)
    }
}