package ae.ayeaye.guavapay.domain

import ae.ayeaye.guavapay.db.AppDatabase
import ae.ayeaye.guavapay.db.Token
import ae.ayeaye.guavapay.ui.first.Token.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PricesRepository @Inject constructor(
    private val retrofitService: RestApi,
    private val db: AppDatabase,
) {

    suspend fun checkPriceForToken(code: String): Float {
        val price =  when (code) {
            BTC.code -> retrofitService.getBitcoinPrice().bitcoin.usd
            ETH.code -> retrofitService.getEthereumPrice().ethereum.usd
            XRP.code -> retrofitService.getRipplePrice().ripple.usd
            else -> -1f
        }

        return price
    }

    fun saveGoodPrice(code: String, price: Float) {
        db.tokenDao().insert(Token(code, price))
    }

    fun getPrices(code: String): Flow<List<Token>> {
        return db.tokenDao().getAll(code)

    }
}