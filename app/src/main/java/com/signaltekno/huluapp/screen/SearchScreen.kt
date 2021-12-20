package com.signaltekno.huluapp.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.signaltekno.huluapp.R
import com.signaltekno.huluapp.component.AnimatedShimmer
import com.signaltekno.huluapp.component.MovieItem
import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.ui.theme.OnBoardColor
import com.signaltekno.huluapp.ui.theme.Text700
import com.signaltekno.huluapp.viewmodel.SharedViewModel

@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
    setBottomBar: (Boolean) -> Unit
) {
    var query by rememberSaveable {mutableStateOf("")}
    var firstLoad by remember{ mutableStateOf(true)}
    val dataSearch by sharedViewModel.dataSearch.observeAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = firstLoad){
        if(firstLoad){
            setBottomBar(true)
        }
        firstLoad = false
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier= Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.OnBoardColor)){
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Text700
                )
            },
            placeholder = {
                Text(
                    text = "Judul film",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = Text700,
                    maxLines = 1
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            textStyle = TextStyle(
                color = Text700,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.body1.fontSize
            ),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Text700
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                Log.d("Search", query)
                sharedViewModel.doSearch(query)
                keyboardController?.hide()
            })
        )
        if(dataSearch is NetworkResult.Idle){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_movie_24),
                    contentDescription = "Search",
                    modifier = Modifier.size(150.dp),
                    tint = Text700
                )
                Text(
                    text = "Ketik judul film untuk memulai pencarian",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    color = Text700,
                    modifier=Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        if(dataSearch is NetworkResult.Loading){
            repeat(7) {
                AnimatedShimmer()
            }
        }

        if(dataSearch is NetworkResult.Success){
            val items = dataSearch?.data?.results
            if(items != null) {
                LazyColumn(state=listState) {
                    items(items) { item ->
                        MovieItem(item = item, navController, sharedViewModel = sharedViewModel, setBottombar = setBottomBar)
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}