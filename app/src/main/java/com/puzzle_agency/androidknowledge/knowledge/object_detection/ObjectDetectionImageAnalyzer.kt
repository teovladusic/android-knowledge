package com.puzzle_agency.androidknowledge.knowledge.object_detection

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class ObjectDetectionImageAnalyzer(
    private val objectDetectorHelper: ObjectDetectorHelper
) : ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val imageRotation = image.imageInfo.rotationDegrees
            objectDetectorHelper.detect(image.toBitmap(), imageRotation)
        }
        frameSkipCounter++

        image.close()
    }
}
