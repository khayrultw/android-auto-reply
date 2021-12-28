package com.khayrul.doauto.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.khayrul.doauto.R
import com.khayrul.doauto.presentation.auto_reply.AutoReply
import com.khayrul.doauto.presentation.home.Home
import com.khayrul.doauto.presentation.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val scaffoldState = rememberScaffoldState( rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
            Drawer(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }
    ) {
        Navigation(
            navController = navController
        )
    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(
        title = { Text(text = "Doauto", fontSize = 18.sp)},
        navigationIcon = {
            IconButton(onClick = {scope.launch { scaffoldState.drawerState.open() }}) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Icon")
            }
        },
        backgroundColor = Color.LightGray,
        contentColor = Color.Black
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    val items = listOf(
        Screen.Home,
        Screen.AutoReply
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.like),
                contentDescription = "logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route ) {
                navController.navigate(item.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }

    }
}

@Composable
fun DrawerItem(item: Screen, selected: Boolean, onItemClick: (Screen) -> Unit) {
    val background = if(selected) colorResource(R.color.grey200) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .padding(start = 10.dp, end = 10.dp)
            .background(background)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(color = Color.Black)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = item.title)
    }
}

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.AutoReply.route
    ) {
        composable(route = Screen.Home.route) {
            Home()
        }
        composable(route = Screen.AutoReply.route) {
            AutoReply()
        }
    }
}