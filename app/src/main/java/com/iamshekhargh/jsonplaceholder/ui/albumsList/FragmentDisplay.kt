package com.iamshekhargh.jsonplaceholder.ui.albumsList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.iamshekhargh.jsonplaceholder.R
import com.iamshekhargh.jsonplaceholder.databinding.FragmentDisplayListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 2:50 AM
 */
@AndroidEntryPoint
class FragmentDisplay : Fragment(R.layout.fragment_display_list), TabLayout.OnTabSelectedListener {

    private val viewModel: DisplayFragmentViewModel by viewModels()
    lateinit var binding: FragmentDisplayListBinding
    lateinit var adapter: AlbumItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDisplayListBinding.bind(view)
        adapter = AlbumItemAdapter()

        binding.apply {
            displayFragmentRv.layoutManager = GridLayoutManager(context, 3)
            displayFragmentRv.adapter = adapter

            displayFragmentTabLayout.apply {
                tabGravity = TabLayout.GRAVITY_FILL
                tabMode = TabLayout.MODE_SCROLLABLE
            }

            viewModel.tabList?.forEach { item ->
                displayFragmentTabLayout.addTab(
                    displayFragmentTabLayout.newTab().setText(item.title)
                )

                viewModel.mapOfIds.put(item.title, item.id.toString())
            }

            displayFragmentTabLayout.addOnTabSelectedListener(this@FragmentDisplay)
        }

        listenForEvents()
        appLogic()
    }

    private fun appLogic() {
        viewModel.responseAsLiveData.observe(viewLifecycleOwner) { albumIdResoponse ->
            showProgressBar(false)
            adapter.submitList(albumIdResoponse)
        }

        viewModel.firstTimeEnteringFragment()
    }

    private fun listenForEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is Events.ShowSnackbar -> {
                        showSnackbar(event.text)
                    }
                    is Events.PageChanged -> {
                        showProgressBar(true)
                        viewModel.getList(event.text)
                    }
                }
            }
        }
    }


    // Helper Functions

    private fun showSnackbar(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
    }

    private fun showProgressBar(show: Boolean) {
        binding.apply {
            displayFragmentProgressbar.isVisible = show
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val item = tab?.text
        viewModel.mapOfIds[item]?.let {
            viewModel.setAlbumId(it)
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }


}