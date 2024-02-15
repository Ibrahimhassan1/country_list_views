package com.softups.countrylist.ui.country_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.softups.countrylist.R
import com.softups.countrylist.databinding.FragmentCountryListBinding
import kotlinx.coroutines.launch

class CountryListFragment : Fragment(R.layout.fragment_country_list) {

    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!

    private val countryViewModel: CountryViewModel by viewModels { CountryViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        val statusTextView = binding.statusTextView
        val countryListRecyclerView: RecyclerView = binding.countryListRecyclerView
        val swipeToRefresh: SwipeRefreshLayout = binding.swipeToRefresh

        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
            countryViewModel.refreshCountryList()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from countryListStateFlow when the lifecycle is STARTED
                // and stops collection when the lifecycle is STOPPED
                countryViewModel.countryListState.collect {
                    if (it.isLoading) {
                        statusTextView.text = getString(R.string.loading_status)
                        countryListRecyclerView.visibility = View.GONE
                    } else if (it.error.isNotBlank()) {
                        statusTextView.isVisible = true
                        statusTextView.text = it.error
                        countryListRecyclerView.visibility = View.GONE
                    } else {
                        if (it.data.isEmpty()) {
                            statusTextView.isVisible = true
                            statusTextView.text = getString(R.string.no_countries_found)
                        } else {
                            statusTextView.isVisible = false
                            countryListRecyclerView.visibility = View.VISIBLE
                            val countryListRecyclerViewAdapter =
                                CountryListRecyclerViewAdapter(it.data)
                            countryListRecyclerView.adapter = countryListRecyclerViewAdapter
                        }
                    }
                }
            }
        }
    }
}