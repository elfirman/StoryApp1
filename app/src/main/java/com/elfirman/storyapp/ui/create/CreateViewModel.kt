package com.elfirman.storyapp.ui.create

import androidx.lifecycle.ViewModel
import com.elfirman.storyapp.data.AuthRepository
import com.elfirman.storyapp.data.StoryRepository
import com.elfirman.storyapp.data.remote.response.FileUploadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {


    fun getAuthToken(): Flow<String?> = authRepository.getAuthToken()


    suspend fun uploadImage(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<Result<FileUploadResponse>> =
        storyRepository.uploadImage(token, file, description)
}