package it.branjsmo.memorygame.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


// Definizione di una carta del Memory
data class MemoryCard(
    val id: Int,
    val icon: ImageVector,
    val color: Color,
    val pairId: Int
)
