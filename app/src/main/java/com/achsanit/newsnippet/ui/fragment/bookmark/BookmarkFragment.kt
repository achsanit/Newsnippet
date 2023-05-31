package com.achsanit.newsnippet.ui.fragment.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.achsanit.newsnippet.databinding.FragmentBookmarkBinding
import com.achsanit.newsnippet.ui.adapter.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by viewModel()
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter {
            val action = BookmarkFragmentDirections.actionNavBookmarkToDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListBookmark()

        with(binding) {
            rvBookmark.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun getListBookmark() {
        viewModel.getListBookmark().observe(viewLifecycleOwner) {
            newsAdapter.submitData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}