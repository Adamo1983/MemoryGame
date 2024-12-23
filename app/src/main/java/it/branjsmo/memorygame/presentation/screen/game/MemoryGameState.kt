package it.branjsmo.memorygame.presentation.screen.game

import it.branjsmo.memorygame.presentation.model.CardState
import it.branjsmo.memorygame.presentation.model.Player

data class MemoryGameState(
    val cardStates: List<CardState> = emptyList(),
    val players: List<Player> = emptyList(),
    val currentPlayerIndex: Int = 0,
    val selectedCards: List<Int> = emptyList(),
    val gameStarted: Boolean = false,
    val movesCount: Int = 0,
    val isGameOver: Boolean = false,
    val showInitialCards: Boolean = false
)
