package com.signaltekno.huluapp.screen

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.signaltekno.huluapp.Constant
import com.signaltekno.huluapp.R
import com.signaltekno.huluapp.model.DetailMovie
import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.ui.theme.OnBoardColor
import com.signaltekno.huluapp.ui.theme.Text700
import com.signaltekno.huluapp.viewmodel.SharedViewModel
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import java.net.URLDecoder

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailScreen(
    navController: NavHostController,
    setBottomBar: (Boolean) -> Unit,
    sharedViewModel: SharedViewModel
) {
    var firstLoad by remember{ mutableStateOf(true)}
    val hasil by sharedViewModel.detail
    val isFav = sharedViewModel.isFav
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    LaunchedEffect(key1 = firstLoad){
        if(firstLoad) {
            setBottomBar(false)
        }
        delay(100)
        firstLoad = false
    }

//    Log.d("hasil-1", hasil!!.adult.toString())

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue =
        if (currentSheetFraction == 1f)
            24.dp
        else
            0.dp
    )

    if(!firstLoad) {
        BottomSheetScaffold(
            sheetShape = RoundedCornerShape(
                topStart = radiusAnim,
                topEnd = radiusAnim
            ),
            scaffoldState = scaffoldState,
            sheetPeekHeight = 80.dp,
            sheetContent = {
//            Log.d("hasil-2", "K")
                hasil?.let { BottomSheetContent(it, isFav = isFav, sharedViewModel = sharedViewModel) }
            },
            content = {
//            Log.d("hasil-3", "Y")
                hasil?.let {
                    BackgroundContent(it.poster_path ?: it.backdrop_path.toString()) {
                        navController.popBackStack()
                    }
                }
            }
        )
    }
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }

@ExperimentalCoilApi
@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
) {
    val imageUrl = "${Constant.URL_ASSET}${heroImage}"
    val painter = rememberImagePainter(imageUrl) {
        error(R.drawable.popcorn1)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + 0.4f)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(all = 10.dp),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    selectedMovie: DetailMovie,
    sheetBackgroundColor: Color = MaterialTheme.colors.OnBoardColor,
    contentColor: Color = Text700,
    isFav: Boolean,
    sharedViewModel: SharedViewModel
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = 40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedMovie.name ?: selectedMovie.title.toString(),
                color = contentColor,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
            Column(modifier=Modifier
                .weight(2f), horizontalAlignment = Alignment.End){
                IconButton(onClick = {
                    if(isFav){
                        sharedViewModel.deleteFavourite(selectedMovie)
                    }else{
                        sharedViewModel.addFavourite(selectedMovie)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bookmark),
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .size(40.dp), tint = if(!isFav) contentColor else Color.Red
                    )
                }
            }

        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Sinopsis",
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = 16.dp),
            text = selectedMovie.overview,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = 7
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Tanggal rilis",
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = 16.dp),
            text = selectedMovie.release_date ?: selectedMovie.first_air_date.toString(),
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = 7
        )
    }
}