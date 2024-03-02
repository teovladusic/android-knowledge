package com.puzzle_agency.androidknowledge

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.captureRoboImage
import com.puzzle_agency.androidknowledge.ui.Greeting
import com.puzzle_agency.androidknowledge.ui.theme.AndroidKnowledgeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
class RoborazziTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun screenshotTest() {
        composeRule.setContent {
            AndroidKnowledgeTheme {
                Greeting(name = "Test", modifier = Modifier.padding(12.dp))
            }
        }

        composeRule.onRoot().captureRoboImage()
    }
}
