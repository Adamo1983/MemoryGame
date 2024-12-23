package it.branjsmo.memorygame.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import it.branjsmo.memorygame.presentation.screen.MemoryGameState


@Composable
fun GameHeader(gameState: MemoryGameState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (gameState.players.size == 1) {
            Text("Mosse: ${gameState.movesCount}",
                style = MaterialTheme.typography.headlineSmall)
        } else {
            gameState.players.forEachIndexed { index, player ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(player.name)
                    Text(
                        "Punti: ${player.score}",
                        fontWeight = if (index == gameState.currentPlayerIndex)
                            FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}