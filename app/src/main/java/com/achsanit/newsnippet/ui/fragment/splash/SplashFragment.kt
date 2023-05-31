package com.achsanit.newsnippet.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.achsanit.newsnippet.R

class SplashFragment : Fragment() {
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.white)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToNavHome()
            findNavController().navigate(action)
        },2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.on_primary)
    }
}