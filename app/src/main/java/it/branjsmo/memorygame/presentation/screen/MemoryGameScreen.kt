package it.branjsmo.memorygame.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.branjsmo.memorygame.presentation.components.GameGrid
import it.branjsmo.memorygame.presentation.components.GameHeader
import it.branjsmo.memorygame.presentation.components.GameOverDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemoryGameScreen(
    modifier:Modifier = Modifier,
    viewModel: MemoryGameViewModel = koinViewModel(),
    numberOfPairs: Int = 14,
    numberOfPlayers: Int = 2,
    showInitialCards: Boolean = true
) {
    val gameState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeGame(numberOfPairs, numberOfPlayers, showInitialCards)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameHeader(gameState)

        Spacer(modifier = Modifier.height(16.dp))

        GameGrid(gameState, viewModel::onCardClick)

        if (gameState.isGameOver) {
            GameOverDialog(
                gameState = gameState,
                onNewGame = viewModel::resetGame
            )
        }
    }
}