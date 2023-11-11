package com.placek.maja.bmi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiDescriptionScreen(navController: NavController,bmi: Double?) {
    val description = when {
        bmi!! < 18.5 -> stringResource(R.string.underweight_description)
        bmi < 24.9 -> stringResource(R.string.healthy_weight_description)
        bmi < 29.9 -> stringResource(R.string.overweight_description)
        bmi >= 29.9 -> stringResource(R.string.obesity_description)
        else -> "Invalid BMI"
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(text = "BMI Result")
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Your BMI: $bmi",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier
                .background(
                    color = getBMIColor(bmi = bmi),
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
