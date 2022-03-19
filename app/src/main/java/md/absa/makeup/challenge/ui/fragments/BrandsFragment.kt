package md.absa.makeup.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import md.absa.makeup.challenge.common.Constants
import md.absa.makeup.challenge.databinding.FragmentBrandsBinding
import md.absa.makeup.challenge.ui.adapters.BrandsAdapter
import md.absa.makeup.challenge.ui.viewmodels.MakeUpViewModel
import md.ke.dvt.data.network.resource.Status
import timber.log.Timber

@AndroidEntryPoint
class BrandsFragment : Fragment() {

    private val viewModel by viewModels<MakeUpViewModel>()
    private var _binding: FragmentBrandsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrandsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fetchMakeup.setOnClickListener {
            viewModel.fetchMakeup()
            viewModel.makeUpItems.observe(viewLifecycleOwner) { response ->
                when (response.status) {
                    Status.LOADING -> {
                        binding.fetchMakeup.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                        binding.noData.visibility = View.GONE
                        binding.noWifi.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.fetchMakeup.visibility = View.GONE
                        binding.noData.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.noWifi.visibility = View.GONE
                        response.data?.let { data ->
                            Snackbar.make(
                                binding.root,
                                response.message.toString(),
                                LENGTH_LONG
                            ).show()
                            Timber.e(data.toString())
                            observeDBForBrands()
                        }
                    }
                    Status.ERROR -> {
                        binding.fetchMakeup.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.noData.visibility = View.GONE
                        // For now I assume its a network issue, best practice => handle all scenarios
                        binding.noWifi.visibility = View.VISIBLE
                        Snackbar.make(
                            binding.root,
                            response.message.toString(),
                            LENGTH_LONG
                        ).show()
                        Timber.e(response.toString())
                    }
                }
            }
        }

        /**
         * Observe data directly via Flow as it is inserted into Room
         */
        observeDBForBrands()
    }

    private fun observeDBForBrands() {
        viewModel.getBrands()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                if (it.isNotEmpty()) {
                    Timber.tag("BRANDS...").e(it.toString())
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.noData.visibility = View.GONE
                    setupRecyclerView(it)
                } else {
                    Snackbar.make(
                        binding.root,
                        "No brands found",
                        LENGTH_LONG
                    ).show()
                    binding.recyclerView.visibility = View.GONE
                    binding.noData.visibility = View.VISIBLE
                    binding.fetchMakeup.visibility = View.VISIBLE
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setupRecyclerView(brands: List<String>) {
        val adapter = BrandsAdapter(brands)
        binding.recyclerView.layoutManager = GridLayoutManager(requireActivity(), Constants.BRANDS_GRID_LAYOUT_COLUMNS)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
