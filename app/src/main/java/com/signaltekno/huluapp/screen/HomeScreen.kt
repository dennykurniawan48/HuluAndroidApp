package com.signaltekno.huluapp.screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.signaltekno.huluapp.Constant
import com.signaltekno.huluapp.component.CategoryChip
import com.signaltekno.huluapp.component.CustomBottomBar
import com.signaltekno.huluapp.model.BottomNavItem
import com.signaltekno.huluapp.navigation.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.signaltekno.huluapp.component.AnimatedShimmer
import com.signaltekno.huluapp.component.MovieItem
import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.ui.theme.BG
import com.signaltekno.huluapp.ui.theme.OnBoardColor
import com.signaltekno.huluapp.viewmodel.SharedViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    setBottomBar: (Boolean) -> Unit
) {
    var firstLoad by rememberSaveable{ mutableStateOf(true)}
    val dataMovie by sharedViewModel.dataMovie.observeAsState()
    var selectedGenre by rememberSaveable{ mutableStateOf(0)}
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        setBottomBar(true)
    }
    
    LaunchedEffect(key1 = firstLoad){
        if(firstLoad){
            sharedViewModel.getAllMovie(Constant.LIST_GENRE[selectedGenre].url)
        }
        firstLoad = false
    }

    LaunchedEffect(key1 = selectedGenre){
        if(!firstLoad){
            sharedViewModel.getAllMovie(Constant.LIST_GENRE[selectedGenre].url)
        }
    }

    Column(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.OnBoardColor)
        .padding(8.dp)) {

        LazyRow {
            items(Constant.LIST_GENRE) { item ->
                CategoryChip(category = item.title, isSelected = Constant.LIST_GENRE[selectedGenre].title == item.title, onSelectedCategoryChanged = {
                    var pos = 0
                    for (i in Constant.LIST_GENRE) {
                        if(i.title == it) {
                            selectedGenre = pos
                        }
                        pos++
                    }
                })
            }
        }

        if (dataMovie is NetworkResult.Success) {
            Log.d("Hello", dataMovie?.data?.results.toString())
            val items = dataMovie?.data?.results
            if(items != null) {
                LazyColumn(state=listState) {
                    items(items) { item ->
                        MovieItem(item = item, navController, sharedViewModel = sharedViewModel, setBottombar = setBottomBar)
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }
        }

        if (dataMovie is NetworkResult.Error) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
        }

        if (dataMovie is NetworkResult.Loading) {
            repeat(7) {
                AnimatedShimmer()
            }
        }
    }
}