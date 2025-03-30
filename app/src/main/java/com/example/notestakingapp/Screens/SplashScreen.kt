package com.example.notestakingapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestakingapp.R
import kotlinx.coroutines.delay

@Composable
fun splashScreen(nv : NavController) {

    LaunchedEffect(Unit) {
        delay(3000)
        nv.navigate(Route.HomeScreen.route){
            popUpTo(0){ inclusive = true}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = "Splash Image",
            modifier = Modifier
                .height(231.dp)
                .width(231.dp)
        )
        Spacer(Modifier.height(30.dp))
        Text(
            "Note Pad",
            fontSize = 48.sp,
            lineHeight = 52.sp,
            color = Color(0xff703410),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            fontStyle = FontStyle.Italic
        )
        Spacer(Modifier.height(10.dp))
        Text(
            "Take Quick Notes",
            fontSize = 16.sp,
            color = Color(0xff3D3B3B),
            fontWeight = FontWeight.Normal
        )
    }

    Box(Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.BottomCenter){
        Text("designed by Ikram Jamro",
            color = Color(0xff3D3B3B),
            fontSize = 12.sp)
    }

}