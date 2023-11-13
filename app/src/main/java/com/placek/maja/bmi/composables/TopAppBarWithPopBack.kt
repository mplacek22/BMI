package com.placek.maja.bmi.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.placek.maja.bmi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithPopBack(navController: androidx.navigation.NavController) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.bmi_result_title))
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}