package com.beauty.parler.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.beauty.parler.navhost.NavHostScreen
import com.beauty.parler.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PARLERTheme {
                NavHostScreen()
            }
        }
    }
}
