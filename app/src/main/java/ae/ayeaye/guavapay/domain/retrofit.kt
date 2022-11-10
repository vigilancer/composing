package ae.ayeaye.guavapay.domain

import retrofit2.http.GET


// http -v GET https://api.coingecko.com/api/v3/simple/price ids==bitcoin vs_currencies==usd

/**
{
    "bitcoin": {
        "usd": 18563.53
    }
}
*/

class BitcoinPrice(val bitcoin: Price)
class RipplePrice(val ripple: Price)
class EthereumPrice(val ethereum: Price)
class Price(val usd: Float)

interface RestApi {

    @GET("simple/price?ids=bitcoin&vs_currencies=usd")
    suspend fun getBitcoinPrice(): BitcoinPrice

    @GET("simple/price?ids=ethereum&vs_currencies=usd")
    suspend fun getEthereumPrice(): EthereumPrice

    @GET("simple/price?ids=ripple&vs_currencies=usd")
    suspend fun getRipplePrice(): RipplePrice

}
