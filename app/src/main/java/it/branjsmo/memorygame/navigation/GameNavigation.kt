package it.branjsmo.memorygame.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import it.branjsmo.memorygame.presentation.screen.game.MemoryGameScreen
import it.branjsmo.memorygame.presentation.screen.setup.GameSetupScreen


@Composable
fun GameNavigation(
    modifier: Modifier = Modifier
){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.GameSetup.route){

        composable(Screen.GameSetup.route) {
            GameSetupScreen(
                modifier = modifier,
                onStartGame = { pairs, players, showInitial ->
                    navController.navigate(
                        Screen.MemoryGame.createRoute(pairs, players, showInitial)
                    )
                }
            )
        }

        composable(
            route = Screen.MemoryGame.route,
            arguments = listOf(
                navArgument(NavArgument.NUMBER_OF_PAIRS) { type = NavType.IntType },
                navArgument(NavArgument.NUMBER_OF_PLAYERS) { type = NavType.IntType },
                navArgument(NavArgument.SHOW_INITIAL_CARDS) { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val numberOfPairs = backStackEntry.arguments?.getInt(NavArgument.NUMBER_OF_PAIRS) ?: 14
            val numberOfPlayers = backStackEntry.arguments?.getInt(NavArgument.NUMBER_OF_PLAYERS) ?: 2
            val showInitialCards = backStackEntry.arguments?.getBoolean(NavArgument.SHOW_INITIAL_CARDS) ?: true

            MemoryGameScreen(
                modifier = modifier,
                numberOfPairs = numberOfPairs,
                numberOfPlayers = numberOfPlayers,
                showInitialCards = showInitialCards
            )
        }
    }

}