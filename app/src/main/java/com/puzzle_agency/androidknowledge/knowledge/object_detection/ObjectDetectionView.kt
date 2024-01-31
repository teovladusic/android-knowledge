package com.puzzle_agency.androidknowledge.knowledge.object_detection

import android.Manifest
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.puzzle_agency.androidknowledge.knowledge.ui.permission.PermissionRequester
import com.puzzle_agency.androidknowledge.knowledge.ui.view.camera.CameraPreview

object ObjectDetectionView {

    @Composable
    fun Compose(viewModel: ObjectDetectionViewModel = hiltViewModel()) {
        val context = LocalContext.current
        val uiState by viewModel.state.collectAsStateWithLifecycle()

        PermissionRequester(permission = Manifest.permission.CAMERA) {
            println("permission grant result = $it")
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.initTensorflowLite(context)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isReadyToDetectObjects) {
                ObjectDetectionPreview(viewModel)
            } else {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            AnimatedVisibility(
                visible = uiState.detectionsList.isNotEmpty(),
                enter = expandVertically(),
                exit = shrinkVertically(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                DetectionsList(detections = uiState.detectionsList)
            }
        }
    }

    @Composable
    private fun ObjectDetectionPreview(objectDetectorListener: ObjectDetectorHelper.DetectorListener) {
        val context = LocalContext.current

        val objectDetectorHelper = remember {
            ObjectDetectorHelper(context = context, objectDetectorListener = objectDetectorListener)
        }

        val analyzer = remember {
            ObjectDetectionImageAnalyzer(objectDetectorHelper)
        }

        val controller = remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context), analyzer)
            }
        }

        CameraPreview(controller = controller, modifier = Modifier.fillMaxSize())
    }

    @Composable
    private fun DetectionsList(modifier: Modifier = Modifier, detections: List<String>) {
        Column(
            modifier = modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tensorflow sees:",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            detections.forEach {
                Text(text = it, color = Color.Black.copy(alpha = 0.8f))
            }
        }
    }
}
