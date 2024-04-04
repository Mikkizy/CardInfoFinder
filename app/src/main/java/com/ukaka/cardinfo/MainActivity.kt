package com.ukaka.cardinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ukaka.cardinfo.presentation.CardInfoScreen
import com.ukaka.cardinfo.presentation.CardInfoViewModel
import com.ukaka.cardinfo.ui.theme.CardInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val cardInfoViewModel: CardInfoViewModel = hiltViewModel()
                    val cardInfoState by cardInfoViewModel.cardInfoState.collectAsStateWithLifecycle()
                    CardInfoScreen(
                        state = cardInfoState,
                        onEvent = cardInfoViewModel::onEvent,
                        eventFlow = cardInfoViewModel.events,
                        activity = this
                    )
                }
            }
        }
    }
}
