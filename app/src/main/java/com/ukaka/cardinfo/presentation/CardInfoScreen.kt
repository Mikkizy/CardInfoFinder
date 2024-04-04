package com.ukaka.cardinfo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.ukaka.cardinfo.MainActivity
import com.ukaka.cardinfo.utils.AppUtils
import com.ukaka.cardinfo.utils.AppUtils.capitalizeFirstLetter
import com.ukaka.cardinfo.utils.CardChannelEvents
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardInfoScreen(
    state: CardInfoState,
    onEvent: (event: CardInfoEvents) -> Unit,
    eventFlow: SharedFlow<CardChannelEvents>,
    activity: MainActivity
) {
    var cardNumberError by remember {
        mutableStateOf(false)
    }

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        eventFlow.collectLatest { cardEvent ->
            when (cardEvent) {
                is CardChannelEvents.ShowCardDetails -> {
                    showAlertDialog = true
                }
                is CardChannelEvents.ShowError -> {
                    AppUtils.showToast(activity, cardEvent.message, MotionToastStyle.ERROR)
                }
                else -> {}
            }
        }
    }
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            modifier = Modifier.fillMaxWidth(),
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Card Details",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(
                            text = "Brand:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = state.cardBrand,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal
                            ))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(text = "Type:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = state.cardType.capitalizeFirstLetter(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal
                            ))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Text(text = "Bank:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = state.bank,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal
                            ))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Text(text = "Country:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = state.country,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal
                            ))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Button(onClick = { showAlertDialog = false }) {
                            Text(text = "Okay")
                        }
                    }
                }
            }
        }
    }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Welcome to Card Info Finder!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Kindly enter the first 6 - 8 digits of your card",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.cardNumber,
                onValueChange = {
                    onEvent(CardInfoEvents.OnCardNumberEntered(it))
                    cardNumberError = state.cardNumber.length > 8
                },
                label = {
                    Text(text = "Card Number")
                },
                placeholder = {
                    Text(text = "Enter first 6 - 8 digits")
                },
                isError = cardNumberError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    errorBorderColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
            if (cardNumberError) {
                Text(
                    text = "Please do not enter more than 8 digits",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(CardInfoEvents.OnFetchInfoClicked(state.cardNumber))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(204, 122, 0),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                enabled = state.cardNumber.length >= 6
            ) {
                Text(text = "Fetch Details")
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                color = Color.Green,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}