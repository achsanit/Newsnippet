package com.achsanit.newsnippet.ui.fragment.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.achsanit.newsnippet.databinding.FragmentBookmarkBotomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BookmarkBottomSheetFragment(
    private val onCallbackDelete: () -> Unit,
    private val onCallbackShare: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBookmarkBotomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarkBotomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            clDelete.setOnClickListener {
                onCallbackDelete()
                dismiss()
            }
            clShare.setOnClickListener {
                onCallbackShare()
                dismiss()
            }
        }
    }

    companion object {
        const val MODAL_TAG = "BookmarkBottomSheet"
    }
}