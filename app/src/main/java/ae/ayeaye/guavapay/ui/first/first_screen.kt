package ae.ayeaye.guavapay.ui.first

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text

enum class Token(
    val displayName: String,
    val code: String,
) {
    BTC("Bitcoin", "BTC"),
    ETH("Ethereum", "ETH"),
    XRP("Ripple", "XRP"),
}

@ExperimentalFoundationApi
@Composable
fun FirstScreen(onCurrencyClicked: (String) -> Unit) {
    CurrencyList(onCurrencyClicked)
}

@ExperimentalFoundationApi
@Composable
fun CurrencyList(onCurrencyClicked: (String) -> Unit) {
    val items = listOf(
        Token.BTC,
        Token.ETH,
        Token.XRP,
    )

    MaterialTheme() {
        Surface(shape = MaterialTheme.shapes.medium) {
            CurrencyColumn(items, onCurrencyClicked)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CurrencyColumn(list: List<Token>, onCurrencyClicked: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            list,
            key = { it.code }
        ) { m ->
            Row(
                modifier = Modifier.animateItemPlacement()
                    .clickable {
                        onCurrencyClicked.invoke(m.code)
                   },
            ) {
                CurrencyItem(m)
            }
        }
    }
}

@Composable
fun CurrencyItem(item: Token) {
    Text(
        text = item.displayName
    )
}