package it.branjsmo.memorygame.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.branjsmo.memorygame.presentation.components.MemoryGame

@Composable
fun MainScreen(modifier: Modifier = Modifier){
    MemoryGame(
        modifier,
        numberOfPairs = 14,    // 2 * numberOfPairs carte in totale
        numberOfPlayers = 2,
        showInitialCards = true  // Mostra le carte all'inizio
    )
}