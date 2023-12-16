package com.puzzle_agency.androidknowledge.knowledge.design_patterns.dependency_injection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltShowcaseModule {

    @Singleton
    @Provides
    fun provideClassToInject(): ClassToInject = ClassToInject("Happy injection")
}

data class ClassToInject(val message: String)

@HiltViewModel
class HiltShowcaseViewModel @Inject constructor(
    classToInject: ClassToInject
) : ViewModel() {

    init {
        println(classToInject.message)
    }
}

@Composable
fun HiltShowcaseUi(viewModel: HiltShowcaseViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "View Model printed injected message",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}