package com.puzzle_agency.androidknowledge.knowledge.ui.modifier.zoomable

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastForEach
import kotlin.math.abs

/**
 * Customized transform gesture detector.
 *
 * A caller of this function can choose if the pointer events will be consumed.
 * And the caller can implement [onGestureStart] and [onGestureEnd] event.
 *
 * @param onGesture If this lambda returns true, the pointer events will be consumed. If it returns
 * false, the pointer events will not be consumed.
 * @param onGestureStart This lambda is called when a gesture starts.
 * @param onGestureEnd This lambda is called when a gesture ends.
 * @param onTap will be called when single tap is detected.
 * @param onDoubleTap will be called when double tap is detected.
 * @param enableOneFingerZoom If true, enable one finger zoom gesture, double tap followed by
 * vertical scrolling.
 */
suspend fun PointerInputScope.detectTransformGestures(
    onGesture: (centroid: Offset, pan: Offset, zoom: Float, timeMillis: Long) -> Boolean,
    onGestureStart: () -> Unit = {},
    onGestureEnd: () -> Unit = {},
    onTap: (position: Offset) -> Unit = {},
    onDoubleTap: (position: Offset) -> Unit = {},
    enableOneFingerZoom: Boolean = true,
) = awaitEachGesture {
    val firstDown = awaitFirstDown(requireUnconsumed = false)
    onGestureStart()

    var firstUp: PointerInputChange = firstDown
    var isTap = true
    val touchSlop = TouchSlop(viewConfiguration.touchSlop)
    forEachPointerEventUntilReleased { event ->
        if (touchSlop.isPast(event)) {
            val zoomChange = event.calculateZoom()
            val panChange = event.calculatePan()
            if (zoomChange != 1f || panChange != Offset.Zero) {
                val centroid = event.calculateCentroid(useCurrent = true)
                val timeMillis = event.changes[0].uptimeMillis
                val canConsume = onGesture(centroid, panChange, zoomChange, timeMillis)
                if (canConsume) {
                    event.consumePositionChanges()
                }
            }
            isTap = false
        }
        if (event.changes.size > 1) {
            isTap = false
        }
        firstUp = event.changes[0]
    }

    if (firstUp.uptimeMillis - firstDown.uptimeMillis > viewConfiguration.longPressTimeoutMillis) {
        isTap = false
    }

    // Vertical scrolling following a double tap is treated as a zoom gesture.
    if (isTap) handleTap(firstUp, onTap, enableOneFingerZoom, onGesture, onDoubleTap)
    onGestureEnd()
}

private suspend fun AwaitPointerEventScope.handleTap(
    firstUp: PointerInputChange,
    onTap: (position: Offset) -> Unit,
    enableOneFingerZoom: Boolean,
    onGesture: (centroid: Offset, pan: Offset, zoom: Float, timeMillis: Long) -> Boolean,
    onDoubleTap: (position: Offset) -> Unit
) {
    val secondDown = awaitSecondDown(firstUp)
    if (secondDown == null) {
        onTap(firstUp.position)
    } else {
        var isDoubleTap = true
        var secondUp: PointerInputChange = secondDown
        val secondTouchSlop = TouchSlop(viewConfiguration.touchSlop)
        forEachPointerEventUntilReleased { event ->
            if (secondTouchSlop.isPast(event)) {
                if (enableOneFingerZoom) {
                    val panChange = event.calculatePan()
                    val zoomChange = 1f + panChange.y * 0.004f
                    if (zoomChange != 1f) {
                        val centroid = event.calculateCentroid(useCurrent = true)
                        val timeMillis = event.changes[0].uptimeMillis
                        val canConsume = onGesture(centroid, Offset.Zero, zoomChange, timeMillis)
                        if (canConsume) {
                            event.consumePositionChanges()
                        }
                    }
                }
                isDoubleTap = false
            }
            if (event.changes.size > 1) {
                isDoubleTap = false
            }
            secondUp = event.changes[0]
        }

        if (secondUp.uptimeMillis - secondDown.uptimeMillis > viewConfiguration.longPressTimeoutMillis) {
            isDoubleTap = false
        }

        if (isDoubleTap) {
            onDoubleTap(secondUp.position)
        }
    }
}

/**
 * Invoke action for each PointerEvent until all pointers are released.
 *
 * @param action Callback function that will be called every PointerEvents occur.
 */
private suspend fun AwaitPointerEventScope.forEachPointerEventUntilReleased(
    action: (PointerEvent) -> Unit,
) {
    do {
        val event = awaitPointerEvent()
        if (event.changes.fastAny { it.isConsumed }) {
            break
        }
        action(event)
    } while (event.changes.fastAny { it.pressed })
}

/**
 * Await second down or timeout from first up
 *
 * @param firstUp The first up event
 * @return If the second down event comes before timeout, returns it. If not, returns null.
 */
private suspend fun AwaitPointerEventScope.awaitSecondDown(
    firstUp: PointerInputChange
): PointerInputChange? = withTimeoutOrNull(viewConfiguration.doubleTapTimeoutMillis) {
    val minUptime = firstUp.uptimeMillis + viewConfiguration.doubleTapMinTimeMillis
    var change: PointerInputChange
    // The second tap doesn't count if it happens before DoubleTapMinTime of the first tap
    do {
        change = awaitFirstDown()
    } while (change.uptimeMillis < minUptime)
    change
}

/**
 * Consume event if the position is changed.
 */
private fun PointerEvent.consumePositionChanges() {
    changes.fastForEach {
        if (it.positionChanged()) {
            it.consume()
        }
    }
}

/**
 * Touch slop detector.
 *
 * This class holds accumulated zoom and pan value to see if touch slop is past.
 *
 * @param threshold Threshold of movement of gesture after touch down. If the movement exceeds this
 * value, it is judged to be a swipe or zoom gesture.
 */
private class TouchSlop(private val threshold: Float) {
    private var zoom = 1f
    private var pan = Offset.Zero
    private var _isPast = false

    /**
     * Judge the touch slop is past.
     *
     * @param event Event that occurs this time.
     * @return True if the accumulated zoom or pan exceeds the threshold.
     */
    fun isPast(event: PointerEvent): Boolean {
        if (_isPast) {
            return true
        }

        zoom *= event.calculateZoom()
        pan += event.calculatePan()
        val zoomMotion = abs(1 - zoom) * event.calculateCentroidSize(useCurrent = true)
        val panMotion = pan.getDistance()
        _isPast = zoomMotion > threshold || panMotion > threshold

        return _isPast
    }
}