package it.branjsmo.memorygame.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import it.branjsmo.memorygame.presentation.screen.game.MemoryGameState

@Composable
 fun GameOverDialog(
    gameState: MemoryGameState,
    onNewGame: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Game Over!") },
        text = {
            if (gameState.players.size == 1) {
                Text("Hai completato il gioco in ${gameState.movesCount} mosse!")
            } else {
                Column {
                    val maxScore = gameState.players.maxOf { it.score }
                    val winners = gameState.players.filter { it.score == maxScore }

                    if (winners.size > 1) {
                        Text("Pareggio!")
                    } else {
                        Text("Vincitore: ${winners.first().name}")
                    }

                    gameState.players.forEach { player ->
                        Text("${player.name}: ${player.score} punti")
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onNewGame) {
                Text("Nuova Partita")
            }
        }
    )
}
