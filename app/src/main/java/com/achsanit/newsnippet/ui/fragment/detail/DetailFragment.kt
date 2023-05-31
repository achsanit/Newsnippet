package com.achsanit.newsnippet.ui.fragment.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.achsanit.newsnippet.R
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.databinding.FragmentDetailBinding
import com.achsanit.newsnippet.utils.DateHelper
import com.achsanit.newsnippet.utils.setShimmerPlaceholder
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private var isBookmarkLatest by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(args.newsDetail)

        with(binding) {
            ibBackToolbar.setOnClickListener {
                findNavController().popBackStack()
            }

            btnOpenPortal.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)

                if (args.newsDetail.url.isNotBlank()) {
                    intent.data = Uri.parse(args.newsDetail.url)

                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Sorry, url not found..", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            ibBookmark.setOnClickListener {
                if (!isBookmarkLatest) {
                    viewModel.addBookmark(args.newsDetail)
                    Toast.makeText(requireContext(), "insertt....", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteBookmark(args.newsDetail)
                    Toast.makeText(requireContext(), "delete....", Toast.LENGTH_SHORT).show()
                }
            }

            ibShare.setOnClickListener {
                shareNews(args.newsDetail)
            }
        }
    }

    private fun setUpView(data: NewsEntity) {
        with(binding) {
            data.let {
                tvNewsTitle.text = it.title
                tvSourcesAuthor.text = if (it.author.isBlank()) {
                    getString(R.string.text_source_placeholder, it.source)
                } else {
                    getString(R.string.text_author_source_placeholder, it.author, it.source)
                }
                tvDatePublished.text = DateHelper.formatTimeIso86012(it.publishedAt)
                ivNews.load(it.urlToImage) {
                    setShimmerPlaceholder()
                }
                tvContent.text = it.description
            }

            viewModel.getBookmarkByTitle(data.title).observe(viewLifecycleOwner) { result ->
                result?.let {
                    setButtonBookmark(true)
                } ?: run {
                    setButtonBookmark(false)
                }
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

    private fun setButtonBookmark(isBookmark: Boolean) {
        with(binding) {
            if (isBookmark) {
                ibBookmark.load(R.drawable.ic_bookmark_fill)
            } else {
                ibBookmark.load(R.drawable.ic_bookmark)
            }
            isBookmarkLatest = isBookmark
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}