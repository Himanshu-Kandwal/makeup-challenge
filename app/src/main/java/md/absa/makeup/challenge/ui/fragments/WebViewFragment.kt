package md.absa.makeup.challenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import md.absa.makeup.challenge.databinding.FragmentWebviewBinding

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        (requireActivity() as AppCompatActivity).supportActionBar?.title = url ?: "Visiting product url"
        binding.webView.loadUrl(url!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
