package com.jetbrains.handson.mpp.ehsan.ui.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jetbrains.handson.mpp.ehsan.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private val viewModel: HomePageViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val bindings = FragmentHomePageBinding.inflate(inflater)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel

        val newsListAdapter = NewsListAdapter(NewsListAdapter.OnClickListener {
            viewModel.displaySelectedNews(it)
        })

        viewModel.navigateToSelectedNews.observe(viewLifecycleOwner,{
            if(it != null){
                this.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToDetailsFragment(it))
                viewModel.displayNewsComplete()
            }
        })

        bindings.newsList.adapter = newsListAdapter

        viewModel.newsList.observe(viewLifecycleOwner, {
            newsListAdapter.submitList(it)
        })

        viewModel.toastMassage.observe(viewLifecycleOwner,{
            if(it != null){
            Toast.makeText(this.context,it,Toast.LENGTH_LONG).show()
                //display tost compelte
            }
        })
        return bindings.root
    }
}

