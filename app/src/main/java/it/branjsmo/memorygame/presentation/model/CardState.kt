package it.branjsmo.memorygame.presentation.model


// Stato di una carta
data class CardState(
    val card: MemoryCard,
    var isRevealed: Boolean = false,
    var isMatched: Boolean = false
)
