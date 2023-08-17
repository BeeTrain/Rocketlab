package io.rocketlab.app.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.rocketlab.app.presentation.viewmodel.AppViewModel
import io.rocketlab.navigation.NavigationComponent
import io.rocketlab.screen.settings.domain.model.AppTheme
import io.rocketlab.ui.theme.RocketlabTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appTheme by viewModel.appThemeState.collectAsState()
            val useDarkTheme = when (appTheme) {
                AppTheme.AS_IN_SYSTEM -> isSystemInDarkTheme()
                else -> appTheme == AppTheme.DARK
            }
            RocketlabTheme(
                useDarkTheme = useDarkTheme
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationComponent()
                }
            }
        }
    }
}