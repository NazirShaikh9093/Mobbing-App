package com.example.mobbingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobbingapp.ui.create_ticket_screen.CreateTicketScreen
import com.example.mobbingapp.ui.project_create_or_amend_screen.ProjectCreateOrAmendScreen
import com.example.mobbingapp.ui.project_data_screen.ProjectDataScreen
import com.example.mobbingapp.ui.project_settings_screen.ProjectSettingsScreen
import com.example.mobbingapp.ui.projects_screen.ProjectsScreen
import com.example.mobbingapp.ui.theme.MobbingAppTheme
import com.example.mobbingapp.ui.ticket_data_screen.TicketDataScreen
import com.example.mobbingapp.ui.ticket_info_screen.TicketInfoScreen
import com.example.mobbingapp.ui.tickets_screen.TicketsScreen
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
                    startDestination = Routes.PROJECT_SELECTION
                )
                {
                    composable(Routes.PROJECT_SELECTION) {
                        ProjectsScreen(
                            onNavigate = { navController.navigate(it.route) }
                        )
                    }
                    composable(route = Routes.PROJECT_CREATION_OR_AMENDMENT + "?projectId={projectId}",
                        arguments = listOf(
                            navArgument(name = "projectId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                        ProjectCreateOrAmendScreen(
                            onPopBackStack = { navController.popBackStack() }
                        )
                    }
                    composable(route = Routes.TICKET_BOARD + "?projectId={projectId}",
                        arguments = listOf(
                            navArgument(name = "projectId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                        TicketsScreen(
                            onNavigation = { navController.navigate(it.route) }
                        )
                    }
                    composable(route = Routes.TICKET_CREATION + "?projectId={projectId}",
                        arguments = listOf(
                            navArgument(name = "projectId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                        CreateTicketScreen(
                            onNavigation = { navController.navigate(it.route) }
                        )
                    }
                    composable(route = Routes.TICKET_VIEW + "?projectId={projectId}&ticketId={ticketId}",
                        arguments = listOf(
                            navArgument(name = "projectId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "ticketId") {
                                type = NavType.StringType
                                defaultValue = "-1"
                            }
                        )
                    ) {
                        TicketInfoScreen(
                            onNavigation = { navController.navigate(it.route) }
                        )
                    }
                    composable(route = Routes.PROJECT_SETTINGS + "?projectId={projectId}",
                        arguments = listOf(
                            navArgument(name = "projectId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        ProjectSettingsScreen(
                            onNavigation = { navController.navigate(it.route) }
                        )
                    }
                    composable(route = Routes.PROJECT_DATA + "?projectId={projectId}",
                        arguments = listOf(
                            navArgument(name = "projectId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        ProjectDataScreen(
                            onNavigation = { navController.navigate(it.route) }
                        )
                    }
                    composable(route = Routes.TICKET_DATA + "?projectId={projectId}&ticketId={ticketId}",
                        arguments = listOf(
                            navArgument(name = "projectId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "ticketId") {
                                type = NavType.StringType
                                defaultValue = "-1"
                            }
                        )
                    ) {
                        TicketDataScreen(
                            onNavigation = { navController.navigate(it.route) }
                        )
                    }
                }
            }
        }
    }
}