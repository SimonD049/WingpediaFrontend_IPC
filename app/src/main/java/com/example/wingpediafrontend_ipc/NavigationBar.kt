package com.example.wingpediafrontend_ipc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController


sealed class NavIcon {
    data class ImgVec(val icon: ImageVector) : NavIcon()
    data class ImgIc(val resID: Int) : NavIcon()
}

data class NavItems(
    val title: String,
    val selectedIcon: NavIcon,
    val unselectedIcon: NavIcon,
    val hasNews: Boolean,
)

@Composable
fun NavigationBar(navController: NavController) {
    val items = listOf(
        NavItems(
            title = "Home",
            selectedIcon = NavIcon.ImgVec(Icons.Filled.Home),
            unselectedIcon = NavIcon.ImgVec(Icons.Outlined.Home),
            hasNews = false,
        ),
        NavItems(
            title = "Map",
            selectedIcon = NavIcon.ImgVec(Icons.Filled.Map),
            unselectedIcon = NavIcon.ImgVec(Icons.Outlined.Map),
            hasNews = false,
        ),
        NavItems(
            title = "",
            selectedIcon = NavIcon.ImgIc(R.drawable.selected_cammic_icon),
            unselectedIcon = NavIcon.ImgIc(R.drawable.unselected_cammic_icon),
            hasNews = false,
        ),
        NavItems(
            title = "Lifer List",
            selectedIcon = NavIcon.ImgVec(Icons.Filled.Book),
            unselectedIcon = NavIcon.ImgVec(Icons.Outlined.Book),
            hasNews = true,
        ),
        NavItems(
            title = "Settings",
            selectedIcon = NavIcon.ImgVec(Icons.Filled.Settings),
            unselectedIcon = NavIcon.ImgVec(Icons.Outlined.Settings),
            hasNews = false,
        )
    )

    var selectedIconIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIconIndex == index,
                        onClick = { selectedIconIndex = index },
                        icon = {
                            when (val icon = if (index == selectedIconIndex) item.selectedIcon else item.unselectedIcon) {
                                is NavIcon.ImgVec -> Icon(
                                    imageVector = icon.icon,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFF257315)

                                )
                                is NavIcon.ImgIc -> Image(
                                    painter = painterResource(id = icon.resID),
                                    contentDescription = item.title,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .offset(y=10.dp)
                                )
                            }
                        },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (items[selectedIconIndex].title) {
            "Home" -> HomeScreen(navController, modifier = Modifier.padding(innerPadding))
            "Settings" -> SettingsScreen(navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    MaterialTheme {
        NavigationBar(navController = rememberNavController())
    }
}
