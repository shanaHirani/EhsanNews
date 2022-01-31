package com.jetbrains.handson.mpp.ehsan.ui.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jetbrains.handson.mpp.ehsan.databinding.FragmentHomePageBinding
import com.jetbrains.handson.mpp.ehsan.shared.EventObserver
import com.jetbrains.handson.mpp.ehsan.shared.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private val viewModel: HomePageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val bindings = FragmentHomePageBinding.inflate(inflater)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel

        val newsListAdapter = NewsListAdapter(NewsListAdapter.OnClickListener {
            viewModel.displaySelectedNews(it)
        })

        viewModel.navigateToSelectedNews.observe(viewLifecycleOwner, EventObserver {
            this.findNavController().navigate(
                HomePageFragmentDirections.actionHomePageFragmentToDetailsFragment(it)
            )
        })

        bindings.newsList.adapter = newsListAdapter

        viewModel.newsList.observe(viewLifecycleOwner, {
            if (it is NetworkResponse.Success) {
                newsListAdapter.submitList(it.value)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, EventObserver { error ->
            Toast.makeText(this.context, error, Toast.LENGTH_LONG).show()
        })

        return bindings.root
    }
}

