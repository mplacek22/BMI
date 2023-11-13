package com.placek.maja.bmi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.placek.maja.bmi.R
import com.placek.maja.bmi.composables.getBMICategory
import com.placek.maja.bmi.composables.getBMIColor
import com.placek.maja.bmi.viewmodel.BMIViewModel
import com.placek.maja.bmi.viewmodel.UnitMode
import com.placek.maja.bmi.viewmodel.ValueState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModeSelector(selectedMode: UnitMode, updateMode: (UnitMode) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_space))) {
        UnitMode.values().forEach {
            ElevatedFilterChip(selectedMode == it, onClick = { updateMode(it) },
                label = {
                    Text(it.name)
                }
            )
        }
    }
}


@Composable
fun RowScope.ActionButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    val focusManager = LocalFocusManager.current
    Button(
        onClick = { focusManager.clearFocus(); onClick() },
        enabled = enabled,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.small_space)),
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_space))
    ) {
        Text(text, fontSize = 15.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(state: ValueState, onValueChange: (String) -> Unit, label: String) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = state.value,
        isError = state.error != null,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = { Text(text = label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        trailingIcon = {
            Text(text = state.suffix)
        }
    )
}


@Composable
fun MainScreen(navController: NavController, viewModel: BMIViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        TopAppBarWithMenu(navController, viewModel)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.medium_space))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ModeSelector(viewModel.selectedUnitMode, updateMode = viewModel::updateMode)
                CustomTextField(viewModel.heightState, viewModel::updateHeight, stringResource(id = R.string.height))
                CustomTextField(viewModel.weightState, viewModel::updateWeight, stringResource(id = R.string.weight))
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_space)))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_space)),
            ) {
                Text(
                    text = "%.2f".format(viewModel.bmi),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Divider(modifier = Modifier.fillMaxWidth(.7f), thickness = 2.5.dp)
                Text(
                    text = getBMICategory(bmi = viewModel.bmi),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier
                        .background(
                            color = getBMIColor(bmi = viewModel.bmi),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.small_space)),
                        )
                        .padding(dimensionResource(id = R.dimen.small_space))
                )
                Button(
                    onClick = { navController.navigate(Screen.BmiDescriptionScreen.withArgs(viewModel.bmi.toString())) },
                    enabled = viewModel.canCalculate() && viewModel.bmi != 0.0 // bmi was calculated
                ) {
                    Text(stringResource(R.string.view_details))
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_space)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                ActionButton(text = stringResource(R.string.clear), viewModel::clear)
                ActionButton(text = stringResource(R.string.calculate), viewModel::calculate, viewModel.canCalculate())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithMenu(
    navController: NavController,
    viewModel: BMIViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(R.string.title)) },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text =  { Text(text = stringResource(R.string.about_author)) }, onClick = {
                    navController.navigate(Screen.AboutAuthorScreen.route)
                })
                DropdownMenuItem(text = { Text(text = stringResource(R.string.history)) }, onClick = {
                    navController.navigate(Screen.HistoryScreen.withArgs(viewModel.getBMIHistory().joinToString(";")))
                })
            }
        }
    )
}

