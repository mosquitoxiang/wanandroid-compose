package com.illu.demo.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.illu.demo.ui.splash.SplashPage

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: HomeRoute.SPLASH
    AppNavHost(navController = navController, modifier = Modifier)
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute.SPLASH,//第一个启动页
        modifier = modifier
    ) {
        composable(HomeRoute.SPLASH) {
            SplashPage {
                navController.navigate(HomeRoute.MAIN)
            }
        }
        composable(HomeRoute.MAIN) {
            MainPage {
                //点击下方nav

            }
        }
        composable(HomeRoute.HOME) {

        }
        composable(HomeRoute.SYSTEM) {
            EmptyComingSoon()
        }
        composable(HomeRoute.DISCOVER) {
            EmptyComingSoon()
        }
        composable(HomeRoute.NAVIGATION) {
            EmptyComingSoon()
        }
        composable(HomeRoute.MINE) {
            EmptyComingSoon()
        }
    }
}

