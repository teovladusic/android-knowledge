package com.puzzle_agency.androidknowledge.knowledge.object_detection

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tensorflow.lite.task.gms.vision.TfLiteVision
import org.tensorflow.lite.task.gms.vision.detector.Detection
import javax.inject.Inject

@HiltViewModel
class ObjectDetectionViewModel @Inject constructor() :
    ViewModel(), ObjectDetectorHelper.DetectorListener {

    private val _state = MutableStateFlow(ObjectDetectionUiState())
    val state = _state.asStateFlow()

    fun initTensorflowLite(context: Context) {
        val options = TfLiteInitializationOptions.builder()
            .setEnableGpuDelegateSupport(true)
            .build()

        TfLiteVision.initialize(context, options).addOnSuccessListener {
            _state.update { it.copy(isReadyToDetectObjects = true) }
        }.addOnFailureListener {
            // Called if the GPU Delegate is not supported on the device
            TfLiteVision.initialize(context)
                .addOnSuccessListener {
                    _state.update { it.copy(isReadyToDetectObjects = true) }
                }
                .addOnFailureListener {
                    Log.e(
                        "ObjectDetectionViewModel",
                        "initTensorflowLite: Failure init tensorflow lite",
                        it
                    )
                }
        }
    }

    override fun onObjectDetectionError(error: String) {
        Log.e("ObjectDetectionViewModel", "onObjectDetectionError: $error")
    }

    override fun onObjectDetectionResults(
        results: MutableList<Detection>?,
        inferenceTime: Long,
        imageHeight: Int,
        imageWidth: Int
    ) {
        val detections = results?.flatMap { detection ->
            detection.categories.map { category -> category.label }
        }?.distinct() ?: emptyList()

        _state.update {
            it.copy(detectionsList = detections)
        }
    }
}

data class ObjectDetectionUiState(
    val isReadyToDetectObjects: Boolean = false,
    val errorMessage: String? = null,
    val detectionsList: List<String> = emptyList()
)
