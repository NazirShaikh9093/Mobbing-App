package com.example.mobbingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobbingapp.ui.project_create_or_amend.ProjectCreateOrAmendScreen
import com.example.mobbingapp.ui.projects_screen.ProjectsScreen
import com.example.mobbingapp.ui.theme.MobbingAppTheme
import com.example.mobbingapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobbingAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.PROJECT_SELECTION)
                {
                    composable(Routes.PROJECT_SELECTION) {
                        ProjectsScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(Routes.PROJECT_CREATION_OR_AMENDMENT) {
                        ProjectCreateOrAmendScreen(
                            onPopBackStack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}