package ae.ayeaye.guavapay.ui.third

import ae.ayeaye.guavapay.db.Token
import ae.ayeaye.guavapay.ui.SharedViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun PriceHistoryScreen(currencyCode: String, viewModel: SharedViewModel) {
    MaterialTheme() {
        Surface(shape = MaterialTheme.shapes.medium) {
            PricesContent(currencyCode, viewModel)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun PricesContent(currencyCode: String, viewModel: SharedViewModel) {

    val list by viewModel.prices(currencyCode).observeAsState(emptyList())

    val mainScrollState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize(),
        state = mainScrollState,
    ) {
        items(list) { m ->
            Text(m.price.toString())
        }

    }

}