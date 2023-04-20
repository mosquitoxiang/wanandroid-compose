package com.illu.demo.ui.home

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.illu.demo.R

object HomeRoute {
    const val SPLASH = "Splash"
    const val MAIN = "Main"
    const val HOME = "Home"
    const val SYSTEM = "System"
    const val DISCOVER = "Discover"
    const val NAVIGATION = "Navigation"
    const val MINE = "Mine"
}

data class HomeDestination(
    val route: String,
    val icon: Int,
    val iconTextId: Int,
    val unselectedText: Int = R.color.navTextUnelected,
)

class NavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: HomeDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val HOME_BOTTOM_ICON = listOf(
    HomeDestination(
        route = HomeRoute.HOME,
        icon = R.drawable.ic_home_black_24dp,
        iconTextId = R.string.home,
    ),
    HomeDestination(
        route = HomeRoute.SYSTEM,
        icon = R.drawable.ic_equalizer_black_24dp,
        iconTextId = R.string.system
    ),
    HomeDestination(
        route = HomeRoute.DISCOVER,
        icon = R.drawable.ic_layers_black_24dp,
        iconTextId = R.string.find
    ),
    HomeDestination(
        route = HomeRoute.NAVIGATION,
        icon = R.drawable.ic_navigation_black_24dp,
        iconTextId = R.string.navigation
    ),
    HomeDestination(
        route = HomeRoute.MINE,
        icon = R.drawable.ic_person_black_24dp,
        iconTextId = R.string.mine
    )
)