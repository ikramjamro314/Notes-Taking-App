package com.example.notestakingapp.Screens

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.notestakingapp.AppViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable  // ✅ CORRECT IMPORT!
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(vm: AppViewModel = hiltViewModel()) {
    val navController = rememberAnimatedNavController()
    val state = vm.appState.collectAsState()
    val context = LocalContext.current
    AnimatedNavHost(navController, startDestination = Route.SplashScreen.route) {
        composable(
            Route.SplashScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            splashScreen(navController)
        }
        composable(
            Route.CreateRouteScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            CreateNoteScreen(
                appState = state.value,
                onEvent = { vm.insertNotes(context) },
                nv = navController
            )
        }
        composable(
            Route.ViewNoteScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            viewNote(
                nv = navController,
                appState = state.value,
                onDeleteEvent = { vm.deleteNotes(context) },
                onUpdateEvent = {
                    navController.navigate(Route.CreateRouteScreen.route)
                }
            )
        }
        composable(
            Route.HomeScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }
        ) {
            HomeScreen(navController, state.value)
        }
    }
}