package it.branjsmo.memorygame.presentation.screen.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import it.branjsmo.memorygame.R

@Composable
fun GameSetupScreen(
    modifier: Modifier = Modifier,
    onStartGame: (numberOfPairs: Int, numberOfPlayers: Int, showInitialCards: Boolean) -> Unit
) {
    var numberOfPairs by remember { mutableIntStateOf(14) }
    var numberOfPlayers by remember { mutableIntStateOf(2) }
    var showInitialCards by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.setup_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Number of Pairs Slider
        Text(text = stringResource(R.string.pairs_label, numberOfPairs))
        Slider(
            value = numberOfPairs.toFloat(),
            onValueChange = { numberOfPairs = it.toInt() },
            valueRange = 2f..20f,
            steps = 18,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Number of Players Slider
        Text(text = stringResource(R.string.players_label, numberOfPlayers))
        Slider(
            value = numberOfPlayers.toFloat(),
            onValueChange = { numberOfPlayers = it.toInt() },
            valueRange = 1f..4f,
            steps = 3,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Show Initial Cards Switch
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.show_initial_cards))
            Switch(
                checked = showInitialCards,
                onCheckedChange = { showInitialCards = it },
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Button(
            onClick = { onStartGame(numberOfPairs, numberOfPlayers, showInitialCards) },
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(stringResource(R.string.start_game))
        }
    }
}