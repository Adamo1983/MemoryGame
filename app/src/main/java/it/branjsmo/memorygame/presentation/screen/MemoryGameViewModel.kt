package it.branjsmo.memorygame.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.branjsmo.memorygame.presentation.model.CardState
import it.branjsmo.memorygame.presentation.model.MemoryCard
import it.branjsmo.memorygame.presentation.model.Player
import it.branjsmo.memorygame.presentation.theme.Colors
import it.branjsmo.memorygame.presentation.theme.Icons
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoryGameViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(MemoryGameState())
    val uiState = _uiState.asStateFlow()

    private val gameColors = Colors.game
    private val icons = Icons.game

    private fun createCards(numberOfPairs: Int): List<MemoryCard> {
        return List(numberOfPairs) { index ->
            val icon = icons[index % icons.size]
            val color = gameColors[index % gameColors.size]
            List(2) {
                MemoryCard(
                    id = index * 2 + it,
                    icon = icon,
                    color = color,
                    pairId = index
                )
            }
        }.flatten().shuffled()
    }

    private fun showAllCardsTemporarily() {
        viewModelScope.launch {
            _uiState.update { it.copy(
                cardStates = it.cardStates.map { state -> state.copy(isRevealed = true) }
            )}

            delay(2000)

            _uiState.update { it.copy(
                cardStates = it.cardStates.map { state -> state.copy(isRevealed = false) },
                gameStarted = true
            )}
        }
    }

    private suspend fun handlePairSelection() {
        val currentState = _uiState.value
        val (firstIndex, secondIndex) = currentState.selectedCards
        val firstCard = currentState.cardStates[firstIndex].card
        val secondCard = currentState.cardStates[secondIndex].card

        _uiState.update { it.copy(movesCount = it.movesCount + 1) }

        if (firstCard.pairId == secondCard.pairId) {
            // Coppia trovata, aspetta un secondo prima di disabilitare
            delay(1000)
            handleMatchFound()
        } else {
            // Coppia non trovata, aspetta un secondo e nascondi le carte
            delay(1000)
            handleNoMatch()
        }
    }

    private fun handleMatchFound() {
        _uiState.update { state ->
            state.copy(
                cardStates = state.cardStates.mapIndexed { index, cardState ->
                    if (index in state.selectedCards) {
                        cardState.copy(isMatched = true)
                    } else cardState
                },
                players = state.players.mapIndexed { index, player ->
                    if (index == state.currentPlayerIndex) {
                        player.copy(score = player.score + 1)
                    } else player
                },
                selectedCards = emptyList()
            )
        }

        checkGameOver()
    }

    private fun handleNoMatch() {
        _uiState.update { state ->
            state.copy(
                cardStates = state.cardStates.mapIndexed { index, cardState ->
                    if (index in state.selectedCards) {
                        cardState.copy(isRevealed = false)
                    } else cardState
                },
                currentPlayerIndex = (state.currentPlayerIndex + 1) % state.players.size,
                selectedCards = emptyList()
            )
        }
    }

    private fun checkGameOver() {
        val allMatched = _uiState.value.cardStates.all { it.isMatched }
        if (allMatched) {
            _uiState.update { it.copy(isGameOver = true) }
        }
    }

    fun initializeGame(numberOfPairs: Int, numberOfPlayers: Int, showInitialCards: Boolean) {
        val cards = createCards(numberOfPairs)
        val players = List(numberOfPlayers) { Player("Giocatore ${it + 1}") }

        _uiState.update { currentState ->
            currentState.copy(
                cardStates = cards.map { CardState(it) },
                players = players,
                showInitialCards = showInitialCards
            )
        }

        if (showInitialCards) {
            showAllCardsTemporarily()
        } else {
            _uiState.update { it.copy(gameStarted = true) }
        }
    }

    fun onCardClick(index: Int) {
        val currentState = _uiState.value

        if (!currentState.gameStarted ||
            currentState.cardStates[index].isMatched ||
            currentState.cardStates[index].isRevealed ||
            currentState.selectedCards.size >= 2) return

        viewModelScope.launch {
            // Rivela la carta
            _uiState.update { state ->
                state.copy(
                    cardStates = state.cardStates.mapIndexed { i, cardState ->
                        if (i == index) cardState.copy(isRevealed = true) else cardState
                    },
                    selectedCards = state.selectedCards + index
                )
            }

            // Se sono state selezionate due carte
            if (_uiState.value.selectedCards.size == 2) {
                handlePairSelection()
            }
        }
    }

    fun resetGame() {
        val numberOfPlayers = _uiState.value.players.size
        val numberOfPairs = _uiState.value.cardStates.size / 2
        initializeGame(numberOfPairs, numberOfPlayers, _uiState.value.showInitialCards)
    }

}