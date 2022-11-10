package ae.ayeaye.guavapay.ui.third

import ae.ayeaye.guavapay.ui.SharedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs

class PriceHistoryFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private val args: PriceHistoryFragmentArgs by navArgs()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                PriceHistoryScreen(
                    args.currencyCode,
                    viewModel
                )
            }
        }
    }

}