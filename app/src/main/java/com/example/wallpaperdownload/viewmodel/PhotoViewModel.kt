package com.example.wallpaperdownload.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpaperdownload.model.Photo
import com.example.wallpaperdownload.model.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val db:FirebaseFirestore
):ViewModel() {



    private var _photoResult = MutableLiveData<Resource<List<Photo>>>()
    val photoResult get() = _photoResult

    init {
        getPhotos()
    }

    private fun getPhotos(){
        _photoResult.postValue(Resource.loading())
        db.collection("photos").get()
            .addOnSuccessListener { result ->
                val photos = mutableListOf<Photo>()
                for (document in result){
                    val photoUri = document.toObject<Photo>()
                    photoUri.let {
                        photos.add(it)
                    }
                }
                _photoResult.postValue(Resource.success(photos))
            }.addOnFailureListener { exception ->
                _photoResult.postValue(Resource.error(exception.message.toString()))
            }
    }
}