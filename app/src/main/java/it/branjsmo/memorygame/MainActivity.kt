package it.branjsmo.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.branjsmo.memorygame.navigation.GameNavigation
import it.branjsmo.memorygame.presentation.screen.game.MemoryGameScreen
import it.branjsmo.memorygame.presentation.theme.MemoryGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MemoryGameTheme {
        MemoryGameScreen()
    }
}