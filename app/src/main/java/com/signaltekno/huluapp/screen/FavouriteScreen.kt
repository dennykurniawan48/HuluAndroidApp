package com.signaltekno.huluapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.signaltekno.huluapp.component.MovieItem
import com.signaltekno.huluapp.ui.theme.OnBoardColor
import com.signaltekno.huluapp.ui.theme.Text700
import com.signaltekno.huluapp.viewmodel.SharedViewModel

@Composable
fun FavouriteScreen(navController: NavHostController, sharedViewModel: SharedViewModel, setBottomBar: (Boolean)->Unit) {
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = true) {
        setBottomBar(true)
    }
    val dataMovie by sharedViewModel.dataLocal.collectAsState(initial = emptyList())
    Column(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.OnBoardColor)) {
        LazyColumn(state = listState) {
            item{
                Text(text = "Daftar Favourite", color=Text700, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.h5)
            }
            items(dataMovie) { item ->
                MovieItem(
                    item = item,
                    navController,
                    sharedViewModel = sharedViewModel,
                    setBottombar = setBottomBar
                )
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}