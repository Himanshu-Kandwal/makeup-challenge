package md.absa.makeup.challenge.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import md.absa.makeup.challenge.R
import md.absa.makeup.challenge.data.api.resource.Status
import md.absa.makeup.challenge.databinding.FragmentProductBinding
import md.absa.makeup.challenge.model.MakeUpItem
import md.absa.makeup.challenge.ui.adapters.SimilarProductsAdapter
import md.absa.makeup.challenge.ui.viewmodels.MakeUpViewModel
import timber.log.Timber

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val viewModel by viewModels<MakeUpViewModel>()

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("product_id")

        viewModel.getProductById(id!!)
        viewModel.singleProduct.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
//                    Toast.makeText(requireActivity(), "LOADING", Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    response.data.let { product ->
                        Timber.tag("PRODUCT").e(product.toString())
                        setupProductView(product)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireActivity(), "ERROR OCCURRED", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupProductView(product: MakeUpItem?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = product?.name?.trim() ?: "Product"

        val imageUrl = product?.image_link ?: product?.api_featured_image
        binding.image.load(imageUrl) {
            placeholder(R.drawable.placeholder)
        }
        binding.name.text = product?.name?.trim()
        binding.price.text = (product?.currency ?: "KES:") + product?.price
        binding.category.text = "Category: " + (product?.category ?: "N/A")
        binding.rating.text = "Rating: " + (product?.rating ?: "N/A")
        binding.description.text = (product?.description ?: "N/A")

        binding.buy.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", product?.product_link)
            findNavController(binding.root).navigate(R.id.action_productFragment_to_webViewFragment, bundle)
        }
        setupSimilarProducts(product?.product_type)
    }

    private fun setupSimilarProducts(productType: String?) {
        viewModel.getProductsByProductType(productType)

        lifecycleScope.launchWhenStarted {
            viewModel.similarProducts.collectLatest { response ->
                when (response.status) {
                    Status.LOADING -> {
                        // Show progress bar
                    }
                    Status.SUCCESS -> {
                        response.data.let { products ->
                            Timber.tag("PRODUCT").e(products.toString())
                            setupSimilarProductsRecyclerView(products)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireActivity(), "AN ERROR OCCURRED", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupSimilarProductsRecyclerView(products: List<MakeUpItem?>?) {
        val adapter = SimilarProductsAdapter(products)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
