package com.achsanit.newsnippet.ui.fragment.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.databinding.FragmentBookmarkBinding
import com.achsanit.newsnippet.ui.adapter.NewsAdapter
import com.achsanit.newsnippet.ui.fragment.modal.BookmarkBottomSheetFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by viewModel()
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
          onClickItem = {
              val action = BookmarkFragmentDirections.actionNavBookmarkToDetailFragment(it)
              findNavController().navigate(action)
          },
          onMoreClick = {
              BookmarkBottomSheetFragment(
                  onCallbackDelete = {
                      viewModel.deleteBookmark(it)
                  },
                  onCallbackShare = {
                      shareNews(it)
                  }
              ).show(childFragmentManager, BookmarkBottomSheetFragment.MODAL_TAG)
          }
        )
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

    private fun shareNews(data: NewsEntity) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "${data.title}\n \n${data.url}")
        }

        val shareIntent = Intent.createChooser(sendIntent,null)
        startActivity(shareIntent)
    }

    private fun getListBookmark() {
        viewModel.getListBookmark().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.clNoBookmarks.visibility = View.VISIBLE
                binding.rvBookmark.visibility = View.GONE
            } else {
                binding.clNoBookmarks.visibility = View.GONE
                binding.rvBookmark.visibility = View.VISIBLE
                newsAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}