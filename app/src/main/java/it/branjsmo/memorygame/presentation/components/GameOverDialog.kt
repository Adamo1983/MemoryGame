package it.branjsmo.memorygame.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.branjsmo.memorygame.R
import it.branjsmo.memorygame.presentation.screen.game.MemoryGameState

@Composable
 fun GameOverDialog(
    gameState: MemoryGameState,
    onNewGame: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.game_over)) },
        text = {
            if (gameState.players.size == 1) {
                Text(stringResource(R.string.finish_game_with_moves, gameState.movesCount))
            } else {
                Column {
                    val maxScore = gameState.players.maxOf { it.score }
                    val winners = gameState.players.filter { it.score == maxScore }

                    if (winners.size > 1) {
                        Text( stringResource(R.string.tie_message))
                    } else {
                        Text(stringResource(R.string.winner_message, winners.first().name))
                    }

                    gameState.players.forEach { player ->
                        Text(stringResource(R.string.player_with_points, player.name, player.score))
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onNewGame) {
                Text(stringResource(R.string.new_game))
            }
        }
    )
}
