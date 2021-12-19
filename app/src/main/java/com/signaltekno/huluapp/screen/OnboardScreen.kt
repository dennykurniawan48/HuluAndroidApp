package com.signaltekno.huluapp.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.signaltekno.huluapp.navigation.Screen
import com.signaltekno.huluapp.model.OnboardModel
import com.signaltekno.huluapp.ui.theme.*
import com.signaltekno.huluapp.ui.theme.Text700
import com.signaltekno.huluapp.viewmodel.SharedViewModel

@ExperimentalPagerApi
@Composable
fun OnboardScreen(
    navController: NavHostController,
    viewModel: SharedViewModel,
    setBottomBar: (Boolean) -> Unit
) {
    val dataBoard = listOf(
        OnboardModel.Board1,
        OnboardModel.Board2,
        OnboardModel.Board3
    )

    val state = rememberPagerState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        setBottomBar(false)
    }
    Log.d("on board", "set false")
    Column(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.OnBoardColor)) {
        HorizontalPager(modifier = Modifier.weight(10f),count = dataBoard.size, state = state) { pos ->
            PagerScreen(onboardData = dataBoard[pos])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            pagerState = state,
            indicatorWidth = 12.dp,
            spacing = 8.dp,
            activeColor = Purple500,
            inactiveColor = Color.LightGray
        )
        FinishButton(state = state, onClick = {
            viewModel.setFinishOnboard()
            navController.navigate(Screen.HOME.route){
                popUpTo(Screen.SPLASH.route){inclusive=true}
            }
//            context.startActivity(Intent(context, HomeActivity::class.java))
//            (context as Activity).finish()
        }, modifier = Modifier.weight(1f), lastPage = dataBoard.size)
    }
}

@Composable
fun PagerScreen(onboardData: OnboardModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onboardData.icon),
            contentDescription = "Onboard Image"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = onboardData.title,
            fontSize = MaterialTheme.typography.h5.fontSize,
            color = Text700,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp),
            text = onboardData.desc,
            fontSize = MaterialTheme.typography.body2.fontSize,
            color = Text800,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalPagerApi
@Composable
fun FinishButton(
    state: PagerState,
    onClick: () -> Unit,
    modifier: Modifier,
    lastPage: Int
) {
    Row() {
        AnimatedVisibility(modifier=modifier.padding(24.dp), visible = state.currentPage == lastPage-1) {
            Button(onClick = onClick) {
                Text(text = "Finish")
            }
        }
    }
}