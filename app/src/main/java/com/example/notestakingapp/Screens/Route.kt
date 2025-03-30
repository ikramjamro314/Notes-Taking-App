package com.example.notestakingapp.Screens

import kotlinx.serialization.Serializable

sealed class Route(val route: String) {
    object CreateRouteScreen: Route("create_note_screen")
    object ViewNoteScreen: Route("view_note_screen")
    object HomeScreen: Route("home_screen")
    object SplashScreen: Route("splash_screen")
}