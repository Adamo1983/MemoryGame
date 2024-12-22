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
import it.branjsmo.memorygame.presentation.model.MemoryCard
import it.branjsmo.memorygame.presentation.model.Player
import it.branjsmo.memorygame.presentation.theme.Colors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MemoryGame(
    modifier: Modifier = Modifier,
    numberOfPairs: Int = 8,
    numberOfPlayers: Int = 2,
    showInitialCards: Boolean = true
) {
    val scope = rememberCoroutineScope()

    val icons = listOf(
        Icons.Default.Favorite,
        Icons.Default.Star,
        Icons.Default.Phone,
        Icons.Default.Email,
        Icons.Default.Person,
        Icons.Default.Home,
        Icons.Default.Settings,
        Icons.Default.ShoppingCart,
        Icons.Default.Build,
        Icons.Default.Delete,
        Icons.Default.CheckCircle,
        Icons.Default.Info,
        Icons.Default.Lock,
        Icons.Default.Search,
        Icons.Default.AccountBox,
        Icons.Default.AddCircle
    )

    val cards = remember {
        List(numberOfPairs) { index ->
            val icon = icons[index % icons.size]
            val color = Colors.game[index % Colors.game.size]
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

    var cardStates by remember { mutableStateOf(cards.map { CardState(it) }) }
    var players by remember { mutableStateOf(List(numberOfPlayers) { Player("Giocatore ${it + 1}") }) }
    var currentPlayerIndex by remember { mutableIntStateOf(0) }
    var selectedCards by remember { mutableStateOf(listOf<Int>()) }
    var gameStarted by remember { mutableStateOf(false) }
    var movesCount by remember { mutableIntStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }

    LaunchedEffect(showInitialCards) {
        if (showInitialCards && !gameStarted) {
            cardStates = cardStates.map { it.copy(isRevealed = true) }
            delay(2000)
            cardStates = cardStates.map { it.copy(isRevealed = false) }
            gameStarted = true
        } else {
            gameStarted = true
        }
    }

    fun onCardClick(index: Int) {
        if (!gameStarted ||
            cardStates[index].isMatched ||
            cardStates[index].isRevealed ||
            selectedCards.size >= 2) return

        scope.launch {
            cardStates = cardStates.mapIndexed { i, state ->
                if (i == index) state.copy(isRevealed = true) else state
            }

            selectedCards = selectedCards + index

            if (selectedCards.size == 2) {
                movesCount++
                val (firstIndex, secondIndex) = selectedCards
                val firstCard = cardStates[firstIndex].card
                val secondCard = cardStates[secondIndex].card

                if (firstCard.pairId == secondCard.pairId) {
                    // Aggiungiamo un delay per vedere le carte accoppiate prima che vengano disabilitate
                    delay(1000)
                    cardStates = cardStates.mapIndexed { i, state ->
                        if (i in selectedCards) state.copy(isMatched = true) else state
                    }
                    players = players.mapIndexed { i, player ->
                        if (i == currentPlayerIndex) player.copy(score = player.score + 1)
                        else player
                    }
                } else {
                    delay(1000)
                    cardStates = cardStates.mapIndexed { i, state ->
                        if (i in selectedCards) state.copy(isRevealed = false) else state
                    }
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size
                }
                selectedCards = emptyList()

                if (cardStates.all { it.isMatched }) {
                    isGameOver = true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (numberOfPlayers == 1) {
                Text("Mosse: $movesCount", style = MaterialTheme.typography.headlineSmall)
            } else {
                players.forEachIndexed { index, player ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(player.name)
                        Text(
                            "Punti: ${player.score}",
                            fontWeight = if (index == currentPlayerIndex) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cardStates.indices.toList()) { index ->
                MemoryCardItem(
                    cardState = cardStates[index],
                    onClick = { onCardClick(index) }
                )
            }
        }

        if (isGameOver) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Game Over!") },
                text = {
                    if (numberOfPlayers == 1) {
                        Text("Hai completato il gioco in $movesCount mosse!")
                    } else {
                        Column {
                            val maxScore = players.maxOf { it.score }
                            val winners = players.filter { it.score == maxScore }

                            if (winners.size > 1) {
                                Text("Pareggio!")
                            } else {
                                Text("Vincitore: ${winners.first().name}")
                            }

                            players.forEach { player ->
                                Text("${player.name}: ${player.score} punti")
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        cardStates = cards.map { CardState(it) }
                        players = players.map { it.copy(score = 0) }
                        currentPlayerIndex = 0
                        selectedCards = emptyList()
                        movesCount = 0
                        isGameOver = false
                        gameStarted = false
                    }) {
                        Text("Nuova Partita")
                    }
                }
            )
        }
    }
}