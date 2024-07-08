package com.example.wallpaperdownload.utils

import android.app.WallpaperManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.wallpaperdownload.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

@AndroidEntryPoint
class BottomSheet(private val imageUrl: String) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            home.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val inputStream = URL(imageUrl).openStream()
                    WallpaperManager.getInstance(requireContext()).setStream(
                        inputStream
                    )

                }
                this@BottomSheet.dismiss()
            }
            lock.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val inputStream = URL(imageUrl).openStream()
                    WallpaperManager.getInstance(requireContext()).setStream(
                        inputStream,
                        null,
                        true,
                        WallpaperManager.FLAG_LOCK
                    )
                }
                this@BottomSheet.dismiss()
            }
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }


}