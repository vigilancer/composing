package ae.ayeaye.guavapay.ui

import ae.ayeaye.guavapay.R
import ae.ayeaye.guavapay.db.AppDatabase
import ae.ayeaye.guavapay.db.Token
import ae.ayeaye.guavapay.domain.PricesRepository
import android.app.Application
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * shared - show notification from any screen
 * android - for context
 */

@HiltViewModel
class SharedViewModel @Inject constructor(
    application: Application,
    private val rep: PricesRepository,
) : AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading



    fun checkPrice(code: String, min: Float?, max: Float?) {
        min ?: return
        max ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            delay(2000)  // testing
            val price = rep.checkPriceForToken(code)
            Log.d("@@@@", "$price")
            _isLoading.value = false

            val isGoodPrice = ((price > max) or (price < min))

            if (isGoodPrice) {
                rep.saveGoodPrice(code, price)
            }

            showNotification(min, max, isGoodPrice, price, code)
        }
    }

    fun prices(code: String): LiveData<List<Token>> {
        return rep.getPrices(code).asLiveData()
    }

    private fun showNotification(
        min: Float,
        max: Float,
        isGoodPrice: Boolean,
        price: Float,
        code: String
    ) {
        val context = getApplication<Application>().applicationContext

        val content = if (isGoodPrice) "$code price $price is out of range [$min ... $max]!"
        else "$code price $price is in range [$min ... $max]"

        val notificationId = 2
        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(
                if (isGoodPrice) R.drawable.ic_launcher_background
                else R.drawable.ic_launcher_foreground
            )
            .setContentTitle("you've got a m-m-m-message!")
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

}