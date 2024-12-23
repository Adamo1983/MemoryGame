package it.branjsmo.memorygame.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.branjsmo.memorygame.presentation.screen.MemoryGameState

@Composable
fun GameGrid(
    gameState: MemoryGameState,
    onCardClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(gameState.cardStates.indices.toList()) { index ->
            MemoryCardItem(
                cardState = gameState.cardStates[index],
                onClick = { onCardClick(index) }
            )
        }
    }
}