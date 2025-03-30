package com.example.notestakingapp.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestakingapp.AppState
import com.example.notestakingapp.loading
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun viewNote(
    appState: AppState= AppState(),
    onDeleteEvent: () -> Unit,
    onUpdateEvent: () -> Unit,
    nv: NavController
) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),

        ) {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable {
                    nv.popBackStack()
                }
                    .padding(10.dp)
                    .padding(top = 4.dp)
            )
            Text(
                appState.title.value,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(10.dp)
                    .fillMaxWidth(0.6f), // Limits width to prevent overlap
            )

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){

                Icon(
                    Icons.Rounded.Delete, contentDescription = "edit",
                    modifier = Modifier.clickable{
                        onDeleteEvent()
                        nv.navigate(Route.HomeScreen.route){
                            popUpTo(Route.HomeScreen.route){inclusive = true}
                        }
                        appState.title.value = ""
                        appState.note.value = ""
                        appState.id.value = 0
                    }
                        .padding(end = 8.dp)
                        .padding(top = 12.dp)
                )

                Icon(
                    Icons.Rounded.Edit, contentDescription = "edit",
                    modifier = Modifier.clickable(
                        onClick = onUpdateEvent)
                        .padding(top = 12.dp)
                )
            }
        }

        Text(
            text = appState.noteDate.value,
            fontSize = 13.sp,
            color = Color(0xff868484),
            modifier = Modifier.padding(start = 60.dp).padding(bottom = 10.dp)
        )

        Spacer(Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth().padding(20.dp).
                background(Color(0xFFF5F5F5), RoundedCornerShape(10.dp))

        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    this@Column.AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                            initialOffsetY = { it }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                    ) {
                                Text(
                                    appState.note.value,
                                    fontSize = 18.sp,
                                    color = Color(0xff3D3B3B),
                                    textAlign = TextAlign.Justify
                                )
                    }
                }
            }
        }

    }
    if (appState.isLoading.value){
        loading()
    }
    }