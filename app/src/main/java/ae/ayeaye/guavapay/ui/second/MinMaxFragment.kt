package ae.ayeaye.guavapay.ui.second

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ae.ayeaye.guavapay.ui.SharedViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class MinMaxFragment : Fragment() {

    //private lateinit var viewModel: SharedViewModel
    private val viewModel: SharedViewModel by activityViewModels()

    private val args: MinMaxFragmentArgs by navArgs()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            //viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            setContent {
                MinMaxScreen(
                    { onOpenHistoryClicked() },
                    args.currencyCode,
                    viewModel
                )
            }
        }
    }

    private fun onOpenHistoryClicked() {
        findNavController().navigate(MinMaxFragmentDirections.openHistory(args.currencyCode))
    }

}