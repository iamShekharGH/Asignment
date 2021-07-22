package com.iamshekhargh.jsonplaceholder.ui.firstFragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.iamshekhargh.jsonplaceholder.R
import com.iamshekhargh.jsonplaceholder.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 1:22 AM
 */
@AndroidEntryPoint
class FragmentFirst : Fragment(R.layout.fragment_first) {
    private val viewModel: FirstFragmentViewModel by viewModels()
    lateinit var binding: FragmentFirstBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFirstBinding.bind(view)

        binding.apply {

        }

        setupEvents()
        appLogic()
    }

    private fun appLogic() {
        viewModel.getAlbums()
        showProgressBar(true)

        viewModel.responseAsLiveData.observe(viewLifecycleOwner) { data ->
            if (data.isEmpty()) {
                showSnackbar("Data Empty, Try again later.")
//                showProgressBar(false)
            } else {
                viewModel.dataArrived(data)
//                showProgressBar(false)
            }
        }
    }

    private fun setupEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is Events.ShowSnackbar -> {
                        showSnackbar(event.text)
                    }
                    is Events.OpenDisplayFragment -> {
                        val directions =
                            FragmentFirstDirections.actionFragmentFirstToFragmentDisplay(event.list)
                        findNavController().navigate(directions)
                    }
                }
            }
        }
    }

    // Class helper functions.

    private fun showSnackbar(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
    }

    private fun showProgressBar(show: Boolean) {
        binding.apply {
            firstFragmentProgressbar.isVisible = show
        }
    }
}