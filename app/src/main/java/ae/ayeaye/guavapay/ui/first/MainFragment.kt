package ae.ayeaye.guavapay.ui.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    //private val viewModel: SharedViewModel by activityViewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                FirstScreen { code -> onCurrencyClicked(code) }
            }
        }
    }

    private fun onCurrencyClicked(code: String) {
        findNavController().navigate(MainFragmentDirections.openMinMax(code))
    }

}