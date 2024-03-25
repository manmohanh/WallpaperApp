package com.example.wallpaperdownload

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperdownload.adpter.PhotoAdapter
import com.example.wallpaperdownload.databinding.ActivityMainBinding
import com.example.wallpaperdownload.model.Photo
import com.example.wallpaperdownload.model.Status
import com.example.wallpaperdownload.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var photoAdapter: PhotoAdapter
    private val photosList:ArrayList<Photo> = arrayListOf()

    private val viewModel:PhotoViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.photoResult.observe(this){ res ->
            binding.progressBar.visibility = View.GONE
            when(res.status){
                Status.SUCCESS -> {
                    photosList.clear()
                    photosList.addAll(res.data!!)
                    photoAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {}
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }


        photoAdapter = PhotoAdapter(this,photosList,::onClicked)
        binding.apply {
            photoRv.hasFixedSize()
            photoRv.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            photoRv.adapter = photoAdapter
        }
    }

    private fun onClicked(photoUri: String){
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("uri",photoUri)
        startActivity(intent)
    }
}