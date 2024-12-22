package it.branjsmo.memorygame.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import it.branjsmo.memorygame.presentation.model.CardState
import it.branjsmo.memorygame.presentation.theme.Colors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MemoryCardItem(
    cardState: CardState,
    onClick: () -> Unit
) {
    val rotation = animateFloatAsState(
        targetValue = if (cardState.isRevealed) 180f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ), label = ""
    )

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    enabled = !cardState.isMatched,
                    onClick = onClick
                )
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 12f * density
                },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        when {
                            cardState.isMatched -> Colors.disabledCardColor
                            rotation.value >= 90f -> cardState.card.color
                            else -> Colors.cardBackColor
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (rotation.value >= 90f) {
                    Icon(
                        imageVector = cardState.card.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer {
                                rotationY = 180f
                            },
                        tint = Color.White
                    )
                } else {
                    Text(
                        "?",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}