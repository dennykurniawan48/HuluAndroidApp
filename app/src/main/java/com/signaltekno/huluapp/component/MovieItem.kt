package com.signaltekno.huluapp.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.signaltekno.huluapp.Constant
import com.signaltekno.huluapp.model.DetailMovie
import com.signaltekno.huluapp.navigation.Screen
import com.signaltekno.huluapp.ui.theme.Text700
import com.signaltekno.huluapp.viewmodel.SharedViewModel
import kotlinx.serialization.json.Json
import java.net.URL
import java.net.URLEncoder

@Composable
fun MovieItem(item: DetailMovie, navHostController: NavHostController, sharedViewModel: SharedViewModel, setBottombar:(Boolean)->Unit) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp).clickable{
                //val jsonElement = Json.encodeToJsonElement(DetailMovie.serializer(), item)
               // setBottombar(false)
                sharedViewModel.setDetail(item)
                val route = Screen.DETAIL.route
                navHostController.navigate(route)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageSrc = item.poster_path ?: item.backdrop_path
        val movieName = item.title ?: item.name
        val released = item.release_date ?: item.first_air_date
        Image(
            painter = rememberImagePainter(Constant.URL_ASSET + imageSrc),
            contentDescription = "Poster",
            modifier = Modifier
                .size(80.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(text=movieName.toString(),
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.body2, color= Text700
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = released.toString(),
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.body2, color= Text700
            )
        }
    }
}