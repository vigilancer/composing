package ae.ayeaye.guavapay.ui.second

import ae.ayeaye.guavapay.ui.SharedViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


data class MinMax(val min: Int = -1, val max: Int = -1) {
    fun isInitialized() = (min > -1) and (max > -1)
}

class MinMaxRepository() {
    fun getMinMax(): MinMax { return MinMax() }
    fun setMinMax(d: MinMax) {}
}

class MinMaxViewModel(private val rep: MinMaxRepository): ViewModel() {
    val isLoading = false

}

@ExperimentalMaterial3Api
@Composable
fun MinMaxScreen(
    onOpenHistoryClicked: () -> Unit,
    currencyCode: String,
    viewModel: SharedViewModel,
) {
    MaterialTheme() {
        Surface {
            Content(onOpenHistoryClicked, currencyCode, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    onOpenHistoryClicked: () -> Unit,
    currencyCode: String,
    viewModel: SharedViewModel
) {
    Box {
        Text(
            text = currencyCode,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.TopStart)
        )
        Prices(
            currencyCode,
            viewModel,
        )
        Button(
            onClick = onOpenHistoryClicked,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(4.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text("Open history")
        }

    }
}

private fun isPriceInputValid(s: String): Boolean {
    return s.isNotBlank() and (s.toFloatOrNull() != null)
}

private fun canEnableCheckPriceButton(
    minPrice: MutableState<TextFieldValue>,
    maxPrice: MutableState<TextFieldValue>
): Boolean {
    return isPriceInputValid(minPrice.value.text) and isPriceInputValid(maxPrice.value.text)
}

@ExperimentalMaterial3Api
@Composable
fun Prices(
    currencyCode: String,
    viewModel: SharedViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 4.dp, end = 4.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        //val pricesCount = rememberSaveable { mutableStateOf(0) }
        val focusManager = LocalFocusManager.current

        val minPrice = rememberSaveable(stateSaver = TextFieldValue.Saver ) {
            mutableStateOf(TextFieldValue())
        }
        val maxPrice = rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

        val isLoading by viewModel.isLoading.collectAsState()

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = minPrice.value,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default
                    .copy(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number ),
                onValueChange = {
                    minPrice.value = it

                    //if (isPriceInputValid(it.text)) pricesCount.value+=1
                    //else pricesCount.value-=1
                },
                label = { Text("min") }
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = maxPrice.value,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default
                    .copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number ),
                onValueChange = {
                    maxPrice.value = it

                    //if (isPriceInputValid(it.text)) pricesCount.value+=1
                    //else pricesCount.value-=1
                },
                label = { Text("max") }
            )
        }
        Button(
            modifier = Modifier.padding(top = 4.dp),
            onClick = {
                focusManager.clearFocus()

                viewModel.checkPrice(
                    currencyCode,
                    minPrice.value.text.toFloatOrNull(),
                    maxPrice.value.text.toFloatOrNull(),
                )
            },
            shape = RoundedCornerShape(2.dp),
            enabled = canEnableCheckPriceButton(minPrice, maxPrice) and !isLoading
        ) {
            Text("Check", style = MaterialTheme.typography.labelLarge)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MinMaxPreview() {
    MinMaxScreen({}, currencyCode = "BTC", viewModel = viewModel())
}
