package com.example.notestakingapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notestakingapp.AppState
import com.example.notestakingapp.AppViewModel
import com.example.notestakingapp.R
import com.example.notestakingapp.loading
import com.example.notestakingapp.ui.theme.Typography
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(
    nv: NavController, appState: AppState = AppState() , vm: AppViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                        nv.navigate(Route.CreateRouteScreen.route)
                    appState.title.value = ""
                    appState.note.value = ""
                    appState.id.value = 0
                          },
                modifier = Modifier.background(Color(0xff703410), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.img),
                    contentDescription = "add",
                    tint = Color.White
                )
            }
        },
        containerColor = Color.White
    ) {
        it
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "All Notes",
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )
            Spacer(Modifier.height(5.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .background(Color(0xffEEF1F7), RoundedCornerShape(10.dp)),
                value = appState.searchText.value,
                onValueChange = { appState.searchText.value = it },
                placeholder = {
                    Text(
                        "Search",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.alpha(0.5f),
                    )
                },
                maxLines = 1,
                singleLine = true ,
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = "search",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xffEEF1F7),
                    unfocusedBorderColor = Color(0xffEEF1F7),
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                )
            )
            Spacer(Modifier.height(20.dp))


            if (appState.notesList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No Notes Found!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                val noteColors = listOf(
                    Color(0xffFCE5C3),
                    Color(0xffE5D9F2),
                    Color(0xffE3F0AF),
                    Color(0xffFAF6E3),
                    Color(0xffCAE0BC)
                )

                val filteredNotes = if (appState.searchText.value.isBlank()) {
                    appState.notesList.sortedByDescending { it.noteDate }
                } else { appState.notesList.filter { note ->
                    note.title.contains(appState.searchText.value, ignoreCase = true) ||
                            note.note.contains(appState.searchText.value, ignoreCase = true)
                }.sortedByDescending { it.noteDate }
                }

                val listState = rememberLazyListState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    state = listState
                ) {

                    items(filteredNotes, key = { it.id }) { note ->
                        val colors = remember { noteColors.random() }
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    appState.title.value = note.title
                                    appState.note.value = note.note
                                    appState.id.value = note.id
                                    nv.navigate(Route.ViewNoteScreen.route)
                                },
                            RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colors
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    note.title,
                                    fontSize = 16.sp,
                                    color = Color(0xff3D3B3B),
                                    modifier = Modifier.padding(4.dp)
                                )
                                val date = LocalDate.now()
                                val formattedDate =
                                    date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

                                Text(
                                    text = "${formattedDate}",
                                    fontSize = 13.sp,
                                    color = Color(0xff868484),
                                    modifier = Modifier.padding(4.dp)
                                )
                                Text(
                                    note.note,
                                    fontSize = 16.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(4.dp),
                                    color = Color(0xff3D3B3B)
                                )
                            }
                        }
                    }
                }
            }
        }
        if (appState.isLoading.value){
            loading()
        }
    }
}
