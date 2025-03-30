package com.example.notestakingapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notestakingapp.data.entity.NotesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.text.format

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AppState())

    private val notes = repository.getAllNotes().flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = runBlocking { repository.getAllNotes().first() } // Fetch immediately
    )


    var appState = combine(
        _state,
        notes
    ) { state, notesAll ->
        state.copy(notesList = notesAll)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppState()
    )


    fun insertNotes(context: Context) {
        appState.value.isLoading.value = true
        val noteData = NotesData(
            id = appState.value.id.value,
            title = if (appState.value.title.value.isBlank()) "Untitled" else _state.value.title.value,
            note = appState.value.note.value
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(noteData)
        }
        Toast.makeText(context, "Note Saved!", Toast.LENGTH_LONG).show()
        appState.value.title.value = ""
        appState.value.note.value = ""
        appState.value.id.value = 0
        appState.value.isLoading.value = false
    }

    fun deleteNotes(context: Context) {
        appState.value.isLoading.value = true
        val noteData = NotesData(
            id = appState.value.id.value,
            title = appState.value.title.value,
            note = appState.value.note.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNode(noteData)
        }
        Toast.makeText(context, "Note Deleted!", Toast.LENGTH_SHORT).show()
        appState.value.title.value = ""
        appState.value.note.value = ""
        appState.value.id.value = 0
        appState.value.isLoading.value = false
    }


}

data class AppState(
    var isLoading: MutableState<Boolean> = mutableStateOf(false),
    val error: String? = null,
    val notesList: List<NotesData> = emptyList(),
    var title: MutableState<String> = mutableStateOf(""),
    var note: MutableState<String> = mutableStateOf(""),
    var id: MutableState<Int> = mutableIntStateOf(0),
    var searchText: MutableState<String> = mutableStateOf(""),
    var noteDate: MutableState<String> = mutableStateOf(
        "${
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }"
    ),
    var isNoteCreated: MutableState<Boolean> = mutableStateOf(false),
    var isNoteUpdated: MutableState<Boolean> = mutableStateOf(false)
)