package it.branjsmo.memorygame.navigation

sealed class Screen(val route: String) {
    data object GameSetup : Screen(Route.GAME_SETUP)
    data object MemoryGame : Screen(
        "${Route.MEMORY_GAME}/{${NavArgument.NUMBER_OF_PAIRS}}/{${NavArgument.NUMBER_OF_PLAYERS}}/{${NavArgument.SHOW_INITIAL_CARDS}}"
    ) {
        fun createRoute(numberOfPairs: Int, numberOfPlayers: Int, showInitialCards: Boolean): String {
            return "${Route.MEMORY_GAME}/$numberOfPairs/$numberOfPlayers/$showInitialCards"
        }
    }
}