package com.example.notestakingapp.Screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notestakingapp.AppState
import com.example.notestakingapp.R
import com.example.notestakingapp.loading
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CreateNoteScreen(
    appState: AppState = AppState(),
    onEvent: () -> Unit,
    nv: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)
        ) {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(10.dp)
                    .padding(top = 4.dp)
                    .clickable {
                        nv.popBackStack()
                    }
            )
            Text(
                "Create Note",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(10.dp)
            )
            val context = LocalContext.current

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Icon(
                    painter = painterResource(R.drawable.view), contentDescription = "view",
                    modifier = Modifier
                        .clickable {
                            if (appState.isNoteCreated.value) {
                                nv.navigate(Route.ViewNoteScreen.route)
                            } else Toast.makeText(
                                context, "Please first create the note!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .size(50.dp)
                        .padding(10.dp)
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .padding(4.dp),
                textStyle = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                value = appState.title.value,
                onValueChange = { appState.title.value = it },
                placeholder = {
                    Text(
                        "Title",
                        fontSize = 30.sp,
                        color = Color(0xff3D3B3B),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.alpha(0.2f)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color(0xff3D3B3B),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color(0xff3D3B3B),
                    focusedPlaceholderColor = Color(0xff3D3B3B),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.height(4.dp))
            Text(
                text = appState.noteDate.value,
                fontSize = 13.sp,
                color = Color(0xff868484),
                modifier = Modifier.padding(start = 20.dp)
            )
        }

        Spacer(Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(10.dp))

        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    this@Column.AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                            initialOffsetY = { it }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                    ) {
                        TextField(
                            textStyle = TextStyle(fontSize = 18.sp),
                            value = appState.note.value,
                            onValueChange = { appState.note.value = it },
                            placeholder = {
                                Text(
                                    "Note something down and save it",
                                    fontSize = 23.sp,
                                    color = Color(0xff3D3B3B),
                                    modifier = Modifier.alpha(0.2f)
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                cursorColor = Color(0xff3D3B3B),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color(0xff3D3B3B),
                                focusedPlaceholderColor = Color(0xff3D3B3B),
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            )
                        )
                    }
                }
            }
        }

        val context = LocalContext.current

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            Button(
                onClick = {
                    if (appState.note.value.isNotEmpty()) {
                        onEvent.invoke()
                        appState.isNoteCreated.value = true
                        nv.navigate(Route.HomeScreen.route) {
                            popUpTo(Route.HomeScreen.route) { inclusive = true }
                        }
                        appState.title.value = ""
                        appState.note.value = ""
                        appState.id.value = 0
                    } else Toast.makeText(
                        context,
                        "Please enter a note",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff703410),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .then(
                        if (appState.note.value.isNotEmpty() || appState.title.value.isNotEmpty()) Modifier.alpha(
                            1f
                        )
                        else Modifier.alpha(0.2f)
                    ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = "Save Note",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
    if (appState.isLoading.value) {
        loading()
    }
}

