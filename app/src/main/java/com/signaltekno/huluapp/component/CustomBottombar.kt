package com.signaltekno.huluapp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.signaltekno.huluapp.model.BottomNavItem
import com.signaltekno.huluapp.ui.theme.BG
import com.signaltekno.huluapp.ui.theme.OnBoardColor

@Composable
fun CustomBottomBar(items: List<BottomNavItem>,
                    navController: NavHostController,
                    onItemClick: (BottomNavItem) -> Unit) {

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.OnBoardColor,
        elevation = 5.dp
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Yellow,
                unselectedContentColor = Color.White,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                        if (selected) {
                            Text(text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp)
                        }
                    }
                }
            )
        }
    }
}