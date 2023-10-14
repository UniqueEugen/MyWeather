package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs.MEMORY_ID_ARG
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs.TITLE_ARG
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs.USER_MESSAGE_ARG
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.PlanetsScreens.ADD_EDIT_MEMORYHOME_SCREEN
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.PlanetsScreens.HOME_SCREEN
import java.util.UUID

private object PlanetsScreens {
    const val HOME_SCREEN = "Home"
    const val ADD_EDIT_MEMORYHOME_SCREEN = "AddEditMemoryScreen"
}

/**
 * Arguments used in [PlanetsDestinations] routes
 */
object MemoriesDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val MEMORY_ID_ARG = "id"
    const val TITLE_ARG = "title"
}

/**
 * Destinations used in the [MainActivity]
 */
object MemoriesDestinations {
    const val MEMORIES_ROUTE = "$HOME_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val ADD_EDIT_MEMORY_ROUTE = "$ADD_EDIT_MEMORYHOME_SCREEN/{$TITLE_ARG}?$MEMORY_ID_ARG={$MEMORY_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class MemoriesNavigationActions(private val navController: NavHostController) {

    fun navigateToMemories(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            HOME_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToAddEditMemory(title: Int, id: UUID?) {
        System.out.println("title is: "+title);
        System.out.println("UUID value in navigation: "+id);
        navController.navigate(
            "${ADD_EDIT_MEMORYHOME_SCREEN}/$title".let {
                System.out.println(if (id != null) "$it?$MEMORY_ID_ARG=$id" else it)
                if (id != null) "$it?$MEMORY_ID_ARG=$id" else it
            }
        )
    }
}