package it.branjsmo.memorygame.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(modifier: Modifier = Modifier){
    MemoryGame(
        modifier,
        numberOfPairs = 14,    // 2 * numberOfPairs carte in totale
        numberOfPlayers = 2,
        showInitialCards = true  // Mostra le carte all'inizio
    )
}