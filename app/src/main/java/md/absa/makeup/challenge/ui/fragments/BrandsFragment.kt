package md.absa.makeup.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                    }
                    Status.SUCCESS -> {
                        binding.fetchMakeup.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        response.data?.let { data ->
                            Toast.makeText(requireActivity(), response.message, Toast.LENGTH_LONG).show()
                            Timber.e(data.toString())
                        }
                    }
                    Status.ERROR -> {
                        binding.fetchMakeup.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), response.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Observe locations via Flow as they are inserted into Room by the Service
        viewModel.getBrands()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                if (it.isNotEmpty()) {
                    Timber.tag("BRANDS").e(it.toString())
                    setupRecyclerView(it)
                } else {
                    Toast.makeText(requireActivity(), "NO BRANDS", Toast.LENGTH_LONG).show()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setupRecyclerView(brands: List<String>) {
        val adapter = BrandsAdapter(brands)
        binding.recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
