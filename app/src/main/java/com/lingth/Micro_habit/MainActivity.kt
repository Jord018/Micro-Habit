package com.lingth.Micro_habit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.lingth.Micro_habit.database.AppDatabase
import com.lingth.Micro_habit.database.User
import com.lingth.Micro_habit.ui.AuroraBackground
import com.lingth.Micro_habit.ui.theme.HabitFlowTheme
import com.lingth.Micro_habit.view.AddHabitScreen
import com.lingth.Micro_habit.view.DashboardScreen
import com.lingth.Micro_habit.view.HabitLibraryScreen
import com.lingth.Micro_habit.view.InsightsScreen
import com.lingth.Micro_habit.view.OnboardingScreen
import com.lingth.Micro_habit.view.ProfileScreen
import com.lingth.Micro_habit.viewmodel.DashboardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HabitFlowTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                // Default to false if currentRoute is null to avoid premature rendering
                val showBars = currentRoute != null && currentRoute != "onboarding"

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBars) {
                            HabitFlowBottomNav(navController)
                        }
                    },
                    floatingActionButton = {
                        if (showBars && currentRoute == "dashboard") {
                            FloatingActionButton(
                                onClick = { navController.navigate("add_habit") },
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add Habit")
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (showBars) {
                            AuroraBackground()
                        }

                        NavHost(
                            navController = navController,
                            startDestination = "onboarding",
                            modifier = Modifier.padding(if (showBars) innerPadding else androidx.compose.foundation.layout.PaddingValues(0.dp))
                        ) {
                            composable("onboarding") {
                                OnboardingScreen(onGetStarted = {
                                    navController.navigate("dashboard") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                })
                            }
                            composable("dashboard") {
                                val viewModel: DashboardViewModel = viewModel()
                                DashboardScreen(viewModel)
                            }
                            composable("library") {
                                val viewModel: DashboardViewModel = viewModel()
                                HabitLibraryScreen(viewModel)
                            }
                            composable("insights") {
                                InsightsScreen()
                            }
                            composable("profile") {
                                ProfileScreen()
                            }
                            composable("add_habit") {
                                AddHabitScreen(onBackClick = {
                                    navController.popBackStack()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HabitFlowBottomNav(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxWidth()
            .height(72.dp),
        color = Color(0xFF131316).copy(alpha = 0.6f),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val items = listOf(
                NavigationItem("dashboard", Icons.Default.Dashboard, "Board"),
                NavigationItem("library", Icons.Default.AutoAwesome, "Library"),
                NavigationItem("insights", Icons.Default.DonutLarge, "Progress"),
                NavigationItem("profile", Icons.Default.Person, "Profile")
            )

            items.forEach { item ->
                val isSelected = currentRoute == item.route

                IconButton(
                    onClick = {
                        if (currentRoute != item.route) navController.navigate(item.route)
                    },
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) Color(0xFF69DBAD) else Color(0xFFCEC3D3).copy(alpha = 0.6f)
                        )
                        if (isSelected) {
                            Text(
                                text = item.label,
                                fontSize = 10.sp,
                                color = Color(0xFF69DBAD),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

data class NavigationItem(val route: String, val icon: ImageVector, val label: String)
