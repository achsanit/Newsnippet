package com.achsanit.newsnippet.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.achsanit.newsnippet.databinding.FragmentHomeBinding
import com.achsanit.newsnippet.ui.adapter.BannerAdapter
import com.achsanit.newsnippet.ui.adapter.NewsAdapter
import com.achsanit.newsnippet.utils.Resource
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private val bannerAdapter: BannerAdapter by lazy {
        BannerAdapter()
    }
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBanner()
        viewModel.getNewsByCategory(newsCategories[0])

        addCategoryToChip(newsCategories)

        setUpBanner()
        setUpRecycler()

        with(binding) {
            searchBar.setOnClickListener {
                Toast.makeText(requireContext(), "Coming Soon..", Toast.LENGTH_SHORT).show()
            }

            chipCategory.setOnCheckedStateChangeListener { _, _ ->
                val selectedChipText = binding.chipCategory.findViewById<Chip>(chipCategory.checkedChipId).text.toString()
                viewModel.getNewsByCategory(selectedChipText)
            }
        }

        obsListTopHeadlines()
        obsNewsByCategory()
    }

    private fun obsListTopHeadlines() {
        viewModel.listBanner.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {
                    binding.pbLoadingVp.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.pbLoadingVp.visibility = View.GONE
                    result.data?.let {
                        bannerAdapter.submitData(it)
                    }
                }
                else -> {
                    binding.pbLoadingVp.visibility = View.GONE
                }
            }
        }
    }

    private fun obsNewsByCategory() {
        viewModel.listNewsByCategory.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {
                    binding.pbLoadingRv.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.pbLoadingRv.visibility = View.GONE
                    result.data?.let {
                        newsAdapter.submitData(it)
                    }
                }
                else -> {
                    binding.pbLoadingRv.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpBanner() {
        val viewPager = binding.vpTopHeadlines
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer { page, p ->
            val r = 1 - abs(p)
            page.scaleY = 0.85f + r * 0.15f
        }

        viewPager.apply {
            adapter = bannerAdapter
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(compositePageTransformer)
        }
    }

    private fun setUpRecycler() {
        with(binding) {
            rvNewsCategory.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }
    private fun addCategoryToChip(categories: List<String>) {

        categories.forEach { category ->
            val chip = Chip(requireContext())
            chip.apply {
                text = category
                isCheckable = true
                id = View.generateViewId()
            }

            binding.chipCategory.addView(chip)

            if(category == categories[0]) binding.chipCategory.check(chip.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val newsCategories = listOf(
            "business",
            "technology",
            "entertainment",
            "general",
            "health",
            "science",
            "sports",
        )
    }
}