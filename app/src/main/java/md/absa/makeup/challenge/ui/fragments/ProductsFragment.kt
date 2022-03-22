package md.absa.makeup.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import md.absa.makeup.challenge.databinding.FragmentProductsBinding
import md.absa.makeup.challenge.model.MakeUpItem
import md.absa.makeup.challenge.ui.adapters.ProductsAdapter
import md.absa.makeup.challenge.ui.viewmodels.MakeUpViewModel
import timber.log.Timber

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel by viewModels<MakeUpViewModel>()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brand = arguments?.getString("brand")

        (requireActivity() as AppCompatActivity).supportActionBar?.title = brand

        // Observe locations via Flow as they are inserted into Room by the Service
        viewModel.getProductsByBrand(brand!!)
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                if (it.isNotEmpty()) {
                    Timber.tag("PRODUCTS").e(it.toString())
                    setupRecyclerView(it)
                } else {
                    Toast.makeText(requireActivity(), "NO PRODUCTS", Toast.LENGTH_LONG).show()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setupRecyclerView(products: List<MakeUpItem>) {
        products.sortedBy { it.category }
        val adapter = ProductsAdapter(products)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
